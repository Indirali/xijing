package com.showtime.xijing.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/27
 * Time: 17:02
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationDatas {

    private String value;

    private String color;

    public NotificationDatas(String value, String color) {
        this.value = value;
        this.color = color;
    }
}
