package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 确认表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:22
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Confirm extends BaseEntity<Long> {

    @ManyToOne
    @NotNull(message = "用户不能为空.")
    private User user;

    @ManyToOne
    @NotNull(message = "确认招聘不能为空.")
    private Recruit recruit;

    @ManyToOne
    @NotNull(message = "确认招聘详情不能为空.")
    private RecruitInfo recruitInfo;

    private LocalDateTime confirmTime;

    private int status;

}
