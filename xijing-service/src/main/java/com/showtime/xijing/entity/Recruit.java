package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.enums.RecruitType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 14:39
 **/
@Data
@Entity
public class Recruit extends BaseEntity<Long> {

    private String title;

    private RecruitType type;

    @ManyToOne
    private User user;

    private String fileIds;

    private String remarks;

    private int status;

    // private List<UserFile> files;

    // private List<RecruitInfo> recruitInfos;
}
