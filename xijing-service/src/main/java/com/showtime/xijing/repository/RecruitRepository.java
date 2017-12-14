package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 12:59
 **/
@Repository
public interface RecruitRepository extends PagingAndSortingRepository<Recruit, Long> {

    Page<Recruit> findAll(Specification<Recruit> specification, Pageable pageable);

}