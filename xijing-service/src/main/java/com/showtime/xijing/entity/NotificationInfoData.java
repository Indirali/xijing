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
public class NotificationInfoData {

    private String value;

    private String color;

    public NotificationInfoData(String value, String color) {
        this.value = value;
        this.color = color;
    }
}
