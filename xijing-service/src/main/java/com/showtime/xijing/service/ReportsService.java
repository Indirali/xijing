package com.showtime.xijing.service;

import com.showtime.xijing.common.utils.LocalDateTimeUtils;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.entity.Reports;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:40
 **/
@Service
public class ReportsService {

    private ReportsRepository reportsRepository;

    @Autowired
    public ReportsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    @CachePut(value = {"findReportById", "findByReportRecruitInfoAndStatus"})
    public Reports save(Reports reports) {
        if (reports.getId() == null) {
            reports.setReportTime(LocalDateTime.now());
        }
        return reportsRepository.save(reports);
    }

    public List<Reports> findAllByUser(User user) {
        LocalDateTime startTime = LocalDateTimeUtils.getDayEnd(LocalDateTimeUtils.minu(LocalDateTime.now(), -30, ChronoUnit.DAYS));
        return reportsRepository.findByUserAndReportTimeBetween(user, startTime, LocalDateTime.now());
    }

    @Cacheable(value = "findReportById")
    public Reports findReportById(long id) {
        return reportsRepository.findOne(id);
    }

    @Cacheable(value = "findByReportRecruitInfoAndStatus")
    public List<Reports> findByReportRecruitInfoAndStatus(RecruitInfo recruitInfo, int status) {
        return reportsRepository.findByReportRecruitInfoAndStatus(recruitInfo, status);
    }

}
