package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.entity.Reports;
import com.showtime.xijing.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 13:04
 **/
@Repository
public interface ReportsRepository extends PagingAndSortingRepository<Reports, Long> {

    int countByReportRecruit(Recruit recruit);

    List<Reports> findByUserAndReportTimeBetween(User user, LocalDateTime startTime, LocalDateTime endTime);

    List<Reports> findByNotificationAndIdIn(boolean notification, Long[] ids);

    List<Reports> findByReportRecruitInfoAndStatus(RecruitInfo recruitInfo, int status);

    List<Reports> findByReportRecruitInfoIdAndNotification(long recruitInfoId, boolean notification);

}
