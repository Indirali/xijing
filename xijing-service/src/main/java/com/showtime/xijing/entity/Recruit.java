package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.enums.RecruitType;
import com.showtime.xijing.enums.SuperStarType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 招聘表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 14:39
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Recruit extends BaseEntity<Long> {

    private String title;

    @Enumerated(EnumType.STRING)
    private RecruitType type;

    @ManyToOne
    private User user;

    private String point;

    private String longitude;

    private String latitude;

    @Enumerated(EnumType.STRING)
    private SuperStarType superStar;

    private boolean urgent;

    private boolean recommend;

    private String remarks;

    private int status;

    private Date participationTime;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<UserFile> userFiles;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<RecruitInfo> recruitInfos;

    @Transient
    private int count;

    @Transient
    private int reportCount;

}
