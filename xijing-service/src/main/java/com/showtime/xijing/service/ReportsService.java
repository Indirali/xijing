package com.showtime.xijing.service;

import com.showtime.xijing.entity.Reports;
import com.showtime.xijing.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public Reports save(Reports reports) {
        if (reports.getId() != null) {
            reports.setUpdateTime(new Date());
        } else {
            reports.setReportTime(new Date());
            reports.setCreateTime(new Date());
        }
        return reportsRepository.save(reports);
    }

}
