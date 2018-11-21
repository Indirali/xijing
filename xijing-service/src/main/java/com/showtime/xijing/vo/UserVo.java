package com.showtime.xijing.vo;

import com.showtime.xijing.entity.User;
import com.showtime.xijing.entity.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/14
 * Time: 15:36
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo {

    private User user;

    private UserInfo userInfo;

}
