package com.showtime.xijing.service;

import com.google.gson.Gson;
import com.showtime.xijing.entity.Confirm;
import com.showtime.xijing.entity.Notification;
import com.showtime.xijing.entity.NotificationInfo;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.repository.ConfirmRepository;
import com.showtime.xijing.repository.NotificationRepository;
import com.showtime.xijing.utils.HttpRequestUtil;
import com.showtime.xijing.utils.WXNotificationUtil;
import com.showtime.xijing.utils.WXRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/28
 * Time: 12:28
 **/
@Slf4j
@Service
public class NotificationService {

    private VerifyCodeService verifyCodeService;
    private ConfirmRepository confirmRepository;
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(VerifyCodeService verifyCodeService,
                               ConfirmRepository confirmRepository,
                               NotificationRepository notificationRepository) {
        this.verifyCodeService = verifyCodeService;
        this.confirmRepository = confirmRepository;
        this.notificationRepository = notificationRepository;
    }

    public Notification save(Notification notification) {
        if (notification.getId() != null) {
            notification.setUpdateTime(new Date());
        } else {
            notification.setCreateTime(new Date());
        }
        return notificationRepository.save(notification);
    }

    public Notification findByUserAndOtherId(User user, long otherId) {
        return notificationRepository.findByUserAndOtherId(user, otherId);
    }

    // 每10分钟执行一次
    @Scheduled(cron = "0 0/10 * * * ?")
    public void notificationSchedule() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR_OF_DAY, 2);
        log.info("当前时间: " + new Date() + "   过两个小时后: " + c.getTime());
        List<Confirm> confirms = confirmRepository.findByStatusAndCreateTimeBetween(0, new Date(), c.getTime());
        for (Confirm confirm : confirms) {
            Notification notification = notificationRepository.findByUserAndOtherId(confirm.getUser(), confirm.getId());
            NotificationInfo notificationInfo = WXNotificationUtil.scheduleNotificationParam(confirm, notification.getNumber());
            String url = WXRequestUtil.sendNotification(verifyCodeService.getWXAccessTokenCache());
            HttpRequestUtil.httpRequest(url, "POST", new Gson().toJson(notificationInfo));
            confirm.setStatus(1);
            confirmRepository.save(confirm);
        }
    }

}
