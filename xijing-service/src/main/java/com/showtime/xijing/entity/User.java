package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 14:22
 **/
@Data
@Entity
public class User extends BaseEntity<Long> {

    private String openId;

    private int place;

    private String nickname;

    private UserFile headPortrait;

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
