package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Confirm;
import com.showtime.xijing.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 13:04
 **/
@Repository
public interface ConfirmRepository extends PagingAndSortingRepository<Confirm, Long> {
    List<Confirm> findByUser(User user);

    Confirm findByUserAndCreateTimeBetween(User user, LocalDateTime startTime, LocalDateTime endTime);

    List<Confirm> findByStatusAndConfirmTimeBetween(int status, LocalDateTime startTime, LocalDateTime endTime);
}
