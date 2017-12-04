package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:24
 **/
@Data
@Entity
public class Follows extends BaseEntity<Long> {

    private User user;

    private User followUser;

    private int status;


}
