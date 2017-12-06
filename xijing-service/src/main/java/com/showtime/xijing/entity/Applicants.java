package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:21
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Applicants extends BaseEntity<Long> {

    private User user;

    private Recruit recruit;

    private int status;

}
