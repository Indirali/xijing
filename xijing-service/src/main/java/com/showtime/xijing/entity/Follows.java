package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * 关注表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:24
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Follows extends BaseEntity<Long> {

    @NotNull(message = "用户不能为空.")
    private User user;

    @NotNull(message = "关注用户不能为空.")
    private User followUser;

    private int status;


}
