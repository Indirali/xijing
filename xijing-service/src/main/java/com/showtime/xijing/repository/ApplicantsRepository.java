package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Applicants;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 13:06
 **/
@Repository
public interface ApplicantsRepository extends PagingAndSortingRepository<Applicants, Long> {
}
