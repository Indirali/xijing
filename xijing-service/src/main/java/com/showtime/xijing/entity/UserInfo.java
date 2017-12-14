package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/14
 * Time: 15:29
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends BaseEntity<Long> {

    private Float chest;

    private Float waist;

    private Float hipline;

    private Float shoeSize;

}
