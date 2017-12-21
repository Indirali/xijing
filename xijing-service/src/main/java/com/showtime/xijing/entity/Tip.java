package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.enums.TipType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 申请表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:21
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Tip extends BaseEntity<Long> {

    @ManyToOne
    private User user;

    private Recruit recruit;

    private TipType tipType;

    private int status;

}
