package com.showtime.xijing.repository;

import com.showtime.xijing.entity.UserInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/14
 * Time: 15:39
 **/
@Repository
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long> {
}
