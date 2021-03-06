package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/28
 * Time: 12:12
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Notification extends BaseEntity<Long> {

    @ManyToOne
    @NotNull(message = "用户不能为空.")
    private User user;

    private Confirm confirmId;

    private RecruitInfo recruitInfoId;

    private String templateType;

    private String number;

    private int count;

    private int status;

}
