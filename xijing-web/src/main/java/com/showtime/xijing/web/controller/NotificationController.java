package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.Notification;
import com.showtime.xijing.service.ActiveMQService;
import com.showtime.xijing.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    private ActiveMQService activeMQService;
    private NotificationService notificationService;

    @Autowired
    public NotificationController(ActiveMQService activeMQService,
                                  NotificationService notificationService) {
        this.activeMQService = activeMQService;
        this.notificationService = notificationService;
    }

    /**
     * 保存通知信息
     *
     * @param notification
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveNotification(Notification notification) {
        notificationService.save(notification);
        return Result.success();
    }

    /**
     * 招聘喜欢的通知
     *
     * @param reportIds
     * @return
     */
    @RequestMapping(value = "/recruitLike", method = RequestMethod.POST)
    public Result sendRecruitLike(Long[] reportIds) {
        activeMQService.recruitInfoPush(reportIds);
        return Result.success();
    }

    /**
     * 结束招聘发送通知
     *
     * @param recruitInfoId
     * @return
     */
    @RequestMapping(value = "/recruitAll", method = RequestMethod.POST)
    public Result sendRecruitAll(long recruitInfoId) {
        activeMQService.recruitInfoAllPush(recruitInfoId);
        return Result.success();
    }

}
