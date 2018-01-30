package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 12:59
 **/
@Repository
public interface RecruitRepository extends PagingAndSortingRepository<Recruit, Long>, JpaSpecificationExecutor<Recruit> {

    List<Recruit> findByUserIn(List<User> users);

    List<Recruit> findByUser(User user);

}
