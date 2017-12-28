package com.showtime.xijing.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/27
 * Time: 16:37
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationInfo {

    private String touser;

    private String template_id;

    private String page;

    private String form_id;

    private Map<String, NotificationInfoData> data;

}
