package com.showtime.xijing.service;

import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.entity.Reports;
import com.showtime.xijing.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        if (reports.getId() != null) {
            reports.setUpdateTime(new Date());
        } else {
            reports.setReportTime(new Date());
            reports.setCreateTime(new Date());
        }
        return reportsRepository.save(reports);
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
