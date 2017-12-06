package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 14:22
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity<Long> {

    private String openId;

    private String place;

    private String nickname;

    private String headPortrait;

    private int sex;

    private int age;

    private String introduction;

    private int role;

    private UserFile wxImage;

    private String wxNumber;

    private String phoneNumber;

    private int creditDegree;

    private int status;

}
