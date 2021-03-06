package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import com.showtime.xijing.enums.UploadType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 用户文件表
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:00
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class UserFile extends BaseEntity<Long> {

    private String fileName;

    private String url;

    private String format;

    @Enumerated(EnumType.STRING)
    private UploadType type;

    private int status;

}
