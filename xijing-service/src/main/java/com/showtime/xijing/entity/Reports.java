package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.enums.ReportType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

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
    private User user;

    private ReportType reportType;

    private String content;

    private User reportUser;

    private Recruit reportRecruit;

    private RecruitInfo reportRecruitInfo;

    private Date reportTime;

    private int status;

}
