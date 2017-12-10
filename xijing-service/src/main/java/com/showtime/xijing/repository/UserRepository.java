package com.showtime.xijing.repository;

import com.showtime.xijing.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:39
 **/
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByOpenId(String openId);

}
