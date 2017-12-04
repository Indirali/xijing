package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:22
 **/
@Data
@Entity
public class Confirm extends BaseEntity<Long> {

    private Recruit recruit;

    private User user;

    private int status;

}
