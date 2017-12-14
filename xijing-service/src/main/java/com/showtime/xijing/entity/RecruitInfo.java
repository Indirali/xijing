package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * 招聘详情
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:16
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class RecruitInfo extends BaseEntity<Long> {

    private Recruit recruit;

    private int sex;

    private int age;

    private boolean organization;

    private int amount;

    private String remarks;

    private int reportCount;

}
