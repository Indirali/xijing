package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 16:05
 **/
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result findOnlyUser(@Validated User user) {
        userService.findByOpenId(user.getOpenId());
        return Result.success();
    }

    @RequestMapping(value = "/allUser", method = RequestMethod.POST)
    public Result findAll(Pageable pageable) {
        return Result.success(userService.findAll(pageable));
    }

}
