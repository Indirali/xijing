package com.showtime.xijing.entity;

import com.showtime.xijing.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
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

    private String type;

    private int status;

}
