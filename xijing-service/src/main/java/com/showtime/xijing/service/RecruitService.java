package com.showtime.xijing.service;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitCondition;
import com.showtime.xijing.repository.RecruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
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

    private RecruitRepository recruitRepository;

    @Autowired
    public RecruitService(RecruitRepository recruitRepository) {
        this.recruitRepository = recruitRepository;
    }

    public Page<Recruit> queryAll(RecruitCondition recruitCondition, Pageable pageable) {
        return recruitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (recruitCondition.getRecruitType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), recruitCondition.getRecruitType()));
            }
            if (recruitCondition.getStartTime() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("participation_time"), recruitCondition.getStartTime()));
            }
            if (recruitCondition.getEndTime() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("participation_time"), recruitCondition.getEndTime()));
            }
            if (recruitCondition.getSuperStar() != null) {
                predicates.add(criteriaBuilder.equal(root.get("super_star"), recruitCondition.getSuperStar()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
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
