package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.enums.RecruitType;
import com.showtime.xijing.enums.SuperStarType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.awt.*;
import java.util.Date;

/**
 * 招聘表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 14:39
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Recruit extends BaseEntity<Long> {

    private String title;

    @Enumerated(EnumType.STRING)
    private RecruitType type;

    @ManyToOne
    private User user;

    private Point point;

    @Enumerated(EnumType.STRING)
    private SuperStarType superStar;

    private String fileIds;

    private String remarks;

    private int status;

    private Date participationTime;

    @Transient
    private int count;

    @Transient
    private int reportCount;

}
