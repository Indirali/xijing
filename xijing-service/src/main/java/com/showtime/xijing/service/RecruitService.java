package com.showtime.xijing.service;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitCondition;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.repository.RecruitRepository;
import com.showtime.xijing.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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

    @Autowired
    public RecruitService(ReportsRepository reportsRepository,
                          RecruitRepository recruitRepository) {
        this.reportsRepository = reportsRepository;
        this.recruitRepository = recruitRepository;
    }

    @Cacheable(value = "findAllRecruitByCondition")
    public Page<Recruit> findAllByCondition(RecruitCondition recruitCondition, Pageable pageable) {
        Page<Recruit> recruits = recruitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
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

    @Cacheable(value = "findAllRecruit")
    public Page<Recruit> findAll(Pageable pageable) {
        Page<Recruit> recruits = recruitRepository.findAll(pageable);
        for (Recruit recruit : recruits)
            recruit.setReportCount(reportsRepository.countByReportRecruit(recruit));
        return recruits;
    }

    @Cacheable(value = "findRecruitById")
    public Recruit findRecruitById(long id) {
        return recruitRepository.findOne(id);
    }

    @Cacheable(value = "findRecruitByUserList")
    public List<Recruit> findRecruitByUserList(List<User> users) {
        return recruitRepository.findByUserIn(users);
    }

    @CachePut(value = {"findRecruitById", "findRecruitByUserList", "findAllRecruit", "findAllRecruitByCondition"})
    public Recruit save(Recruit recruit) {
        return recruitRepository.save(recruit);
    }

    public List<Recruit> findByUser(User user) {
        return recruitRepository.findByUser(user);
    }

}
