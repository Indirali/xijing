package com.showtime.xijing.service;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:39
 **/
@Service
public class RecruitService {

    private RecruitRepository recruitRepository;

    @Autowired
    public RecruitService(RecruitRepository recruitRepository) {
        this.recruitRepository = recruitRepository;
    }

    public Recruit findById(long id) {
        return recruitRepository.findbyId(id);
    }


    public Recruit save(Recruit recruit) {
        if (recruit.getId() != null) {
            recruit.setUpdateTime(new Date());
        } else {
            recruit.setCreateTime(new Date());
        }
        return recruitRepository.save(recruit);
    }
}
