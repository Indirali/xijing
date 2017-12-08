package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Follows;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 13:02
 **/
@Repository
public interface FollowsRepository extends PagingAndSortingRepository<Follows, Long> {
}
