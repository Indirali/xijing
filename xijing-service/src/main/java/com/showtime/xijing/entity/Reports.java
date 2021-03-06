package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.enums.ReportType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 报名表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:26
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Reports extends BaseEntity<Long> {

    @ManyToOne
    @NotNull(message = "用户不能为空.")
    private User user;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String content;

    @ManyToOne
    private Recruit reportRecruit;

    @ManyToOne
    private RecruitInfo reportRecruitInfo;

    private LocalDateTime reportTime;

    private boolean notification;

    // 状态 0 正常 1 喜欢 2 不喜欢 3 取消报名
    private int status;

}
