package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Notification;
import com.showtime.xijing.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/28
 * Time: 12:25
 **/
@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

    Notification findByUserAndConfirmId(User user, long confirmId);

    Notification findByUserAndRecruitInfoId(User user, long recruitInfoId);

}
