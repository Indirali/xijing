package com.showtime.xijing.entity;

import com.showtime.xijing.enums.RecruitType;
import com.showtime.xijing.enums.SuperStarType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/12
 * Time: 14:50
 **/
@Data
public class RecruitCondition {

    private RecruitType recruitType;

    private SuperStarType superStar;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
