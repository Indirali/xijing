package com.showtime.xijing.service;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitCondition;
import com.showtime.xijing.repository.RecruitInfoRepository;
import com.showtime.xijing.repository.RecruitRepository;
import com.showtime.xijing.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:39
 **/
@Service
public class RecruitService {

    private ReportsRepository reportsRepository;
    private RecruitRepository recruitRepository;
    private RecruitInfoRepository recruitInfoRepository;

    @Autowired
    public RecruitService(ReportsRepository reportsRepository,
                          RecruitRepository recruitRepository,
                          RecruitInfoRepository recruitInfoRepository) {
        this.reportsRepository = reportsRepository;
        this.recruitRepository = recruitRepository;
        this.recruitInfoRepository = recruitInfoRepository;
    }

    public Page<Recruit> queryAll(RecruitCondition recruitCondition, Pageable pageable) {
        Page<Recruit> recruits = recruitRepository.findAll((Root<Recruit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (recruitCondition.getRecruitType() != null)
                predicates.add(criteriaBuilder.equal(root.get("type"), recruitCondition.getRecruitType()));
            if (recruitCondition.getSuperStar() != null)
                predicates.add(criteriaBuilder.equal(root.get("super_star"), recruitCondition.getSuperStar()));
            if (recruitCondition.getStartTime() != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("participation_time"), recruitCondition.getStartTime()));
            if (recruitCondition.getEndTime() != null)
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("participation_time"), recruitCondition.getEndTime()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        for (Recruit recruit : recruits)
            recruit.setReportCount(reportsRepository.countByReportRecruit(recruit));
        return recruits;
    }

    public Recruit findById(long id) {
        return recruitRepository.findOne(id);
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