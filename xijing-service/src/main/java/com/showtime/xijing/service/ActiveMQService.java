package com.showtime.xijing.service;

import com.google.gson.Gson;
import com.showtime.xijing.entity.Confirm;
import com.showtime.xijing.entity.Notification;
import com.showtime.xijing.entity.NotificationInfo;
import com.showtime.xijing.entity.Reports;
import com.showtime.xijing.enums.PushType;
import com.showtime.xijing.repository.ConfirmRepository;
import com.showtime.xijing.repository.NotificationRepository;
import com.showtime.xijing.repository.ReportsRepository;
import com.showtime.xijing.utils.HttpRequestUtil;
import com.showtime.xijing.utils.WXNotificationUtil;
import com.showtime.xijing.utils.WXRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/26
 * Time: 17:23
 **/
@Slf4j
@Service
@EnableScheduling
public class ActiveMQService {

    public final ExecutorService pushExecutor = Executors.newFixedThreadPool(20);

    private ReportsRepository reportsRepository;
    private ConfirmRepository confirmRepository;
    private VerifyCodeService verifyCodeService;
    private JmsMessagingTemplate jmsMessagingTemplate;
    private NotificationRepository notificationRepository;

    @Autowired
    public ActiveMQService(ReportsRepository reportsRepository,
                           ConfirmRepository confirmRepository,
                           VerifyCodeService verifyCodeService,
                           JmsMessagingTemplate jmsMessagingTemplate,
                           NotificationRepository notificationRepository) {
        this.reportsRepository = reportsRepository;
        this.confirmRepository = confirmRepository;
        this.verifyCodeService = verifyCodeService;
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.notificationRepository = notificationRepository;
    }

    // 每10分钟执行一次
    @Scheduled(cron = "0 0/1 * * * ?")
    public void userPush() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR_OF_DAY, 2);
        log.info("当前时间: " + new Date() + "   两个小时后: " + c.getTime());
        List<Confirm> confirms = confirmRepository.findByStatusAndCreateTimeBetween(0, new Date(), c.getTime());
        for (Confirm confirm : confirms) {
            pushExecutor.execute(() -> jmsMessagingTemplate.convertAndSend("userQueue", confirm));
        }
    }

    @JmsListener(destination = "userQueue")
    public void userQueue(Confirm confirm) {
        log.info(confirm.toString());
        Notification notification = notificationRepository.findByUserAndConfirmId(confirm.getUser(), confirm.getId());
        NotificationInfo notificationInfo = WXNotificationUtil.scheduleNotificationParam(confirm, notification.getNumber());
        String url = WXRequestUtil.sendNotification(verifyCodeService.getWXAccessTokenCache());
        HttpRequestUtil.httpRequest(url, "POST", new Gson().toJson(notificationInfo));
        confirm.setStatus(1);
        confirmRepository.save(confirm);
    }

    public void recruitInfoPush(Long[] reportIds) {
        List<Reports> reports = reportsRepository.findByNotificationAndIdIn(false, reportIds);
        for (Reports report : reports) {
            pushExecutor.execute(() -> jmsMessagingTemplate.convertAndSend("recruitQueue", report));
        }
    }

    @JmsListener(destination = "recruitQueue")
    public void recruitInfoQueue(Reports report) {
        Notification notification = notificationRepository.findByUserAndRecruitInfoId(report.getUser(), report.getReportRecruitInfo().getId());
        String url = WXRequestUtil.sendNotification(verifyCodeService.getWXAccessTokenCache());
        NotificationInfo notificationString = WXNotificationUtil.recruitPassNotificationParam(report, report.getUser().getOpenId(), notification.getNumber());
        HttpRequestUtil.httpRequest(url, "POST", new Gson().toJson(notificationString));
    }

    public void recruitInfoAllPush(Long recruitInfoId) {
        List<Reports> reports = reportsRepository.findByReportRecruitInfoIdAndNotification(recruitInfoId, false);
        for (Reports report : reports) {
            pushExecutor.execute(() -> jmsMessagingTemplate.convertAndSend("recruitQueue", report));
        }
    }

}
