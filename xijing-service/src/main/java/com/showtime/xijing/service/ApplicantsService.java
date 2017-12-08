package com.showtime.xijing.service;

import com.showtime.xijing.entity.Applicants;
import com.showtime.xijing.repository.ApplicantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 15:20
 **/
@Service
public class ApplicantsService {

    private ApplicantsRepository applicantsRepository;

    @Autowired
    public ApplicantsService(ApplicantsRepository applicantsRepository) {
        this.applicantsRepository = applicantsRepository;
    }

    public Applicants save(Applicants applicants) {
        if (applicants.getId() != null) {
            applicants.setUpdateTime(new Date());
        } else {
            applicants.setCreateTime(new Date());
        }
        return applicantsRepository.save(applicants);
    }
}
