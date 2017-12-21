package com.showtime.xijing.repository;

import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 13:00
 **/
@Repository
public interface RecruitInfoRepository extends PagingAndSortingRepository<RecruitInfo, Long> {

    List<RecruitInfo> findByIdIn(Long[] recruitInfoIds);

    List<RecruitInfo> findByRecruit(Recruit recruit);

    int countByRecruit(long recruitId);

}
