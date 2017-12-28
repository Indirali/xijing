package com.showtime.xijing.service;

import com.showtime.xijing.entity.Notification;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/28
 * Time: 12:28
 **/
@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
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

    public Notification findByUserAndRecruitInfo(User user, RecruitInfo recruitInfo) {
        return notificationRepository.findByUserAndRecruitInfo(user, recruitInfo);
    }

}
