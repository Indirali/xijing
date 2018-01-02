package com.showtime.xijing.web.controller;

import com.google.gson.Gson;
import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.Notification;
import com.showtime.xijing.entity.NotificationInfo;
import com.showtime.xijing.entity.Reports;
import com.showtime.xijing.service.NotificationService;
import com.showtime.xijing.service.ReportsService;
import com.showtime.xijing.service.VerifyCodeService;
import com.showtime.xijing.utils.HttpRequestUtil;
import com.showtime.xijing.utils.WXNotificationUtil;
import com.showtime.xijing.utils.WXRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/28
 * Time: 11:54
 **/
@Slf4j
@RestController
@RequestMapping(value = "/notification")
public class NotificationController {

    private ReportsService reportsService;
    private VerifyCodeService verifyCodeService;
    private NotificationService notificationService;

    @Autowired
    public NotificationController(ReportsService reportsService,
                                  VerifyCodeService verifyCodeService,
                                  NotificationService notificationService) {
        this.reportsService = reportsService;
        this.verifyCodeService = verifyCodeService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveNotification(Notification notification) {
        notificationService.save(notification);
        return Result.success();
    }

    @RequestMapping(value = "/recruitLike", method = RequestMethod.POST)
    public Result findRecruitInfo(Long[] reportIds) {
        List<Reports> reports = reportsService.findByIdIn(reportIds);
        for (Reports report : reports) {
            Notification notification = notificationService.findByUserAndOtherId(report.getUser(), report.getReportRecruitInfo().getId());
            String url = WXRequestUtil.sendNotification(verifyCodeService.getWXAccessTokenCache());
            NotificationInfo notificationString = WXNotificationUtil.recruitPassNotificationParam(report, report.getUser().getOpenId(), notification.getNumber());
            HttpRequestUtil.httpRequest(url, "POST", new Gson().toJson(notificationString));
        }
        return Result.success();
    }

}
