package com.showtime.xijing.service;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.repository.RecruitInfoRepository;
import com.showtime.xijing.repository.RecruitRepository;
import com.showtime.xijing.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:38
 **/
@Service
public class RecruitInfoService {

    private ReportsRepository reportsRepository;
    private RecruitRepository recruitRepository;
    private RecruitInfoRepository recruitInfoRepository;

    @Autowired
    public RecruitInfoService(ReportsRepository reportsRepository,
                              RecruitRepository recruitRepository,
                              RecruitInfoRepository recruitInfoRepository) {
        this.reportsRepository = reportsRepository;
        this.recruitRepository = recruitRepository;
        this.recruitInfoRepository = recruitInfoRepository;
    }

    public List<RecruitInfo> findByRecruitId(long recruitId) {
        Recruit recruit = recruitRepository.findOne(recruitId);
        List<RecruitInfo> recruitInfos = recruitInfoRepository.findByRecruit(recruit);
        recruitInfos.forEach(recruitInfo -> recruitInfo.setReportCount(reportsRepository.countByReportRecruitInfo(recruitInfo)));
        return recruitInfos;
    }

    public List<RecruitInfo> findByIdIn(Long[] recruitInfoIds) {
        return recruitInfoRepository.findByIdIn(recruitInfoIds);
    }

    public RecruitInfo save(RecruitInfo recruitInfo) {
        if (recruitInfo.getId() != null) {
            recruitInfo.setUpdateTime(new Date());
        } else {
            recruitInfo.setCreateTime(new Date());
        }
        return recruitInfoRepository.save(recruitInfo);
    }

    public void delete(long recruitInfoId) {
        recruitInfoRepository.delete(recruitInfoId);
    }
}
