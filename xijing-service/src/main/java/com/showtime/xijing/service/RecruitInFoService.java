package com.showtime.xijing.service;

import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.repository.RecruitInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:38
 **/
@Service
public class RecruitInFoService {

    private RecruitInfoRepository recruitInfoRepository;

    @Autowired
    public RecruitInFoService(RecruitInfoRepository recruitInfoRepository) {
        this.recruitInfoRepository = recruitInfoRepository;
    }

    public RecruitInfo save(RecruitInfo recruitInfo) {
        if (recruitInfo.getId() != null) {
            recruitInfo.setUpdateTime(new Date());
        } else {
            recruitInfo.setCreateTime(new Date());
        }
        return recruitInfoRepository.save(recruitInfo);
    }
}
