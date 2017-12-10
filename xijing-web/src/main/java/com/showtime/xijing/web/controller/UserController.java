package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.common.entity.MobilePhoneNumber;
import com.showtime.xijing.common.entity.PhoneNumber;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 16:05
 **/
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 进入用户主页面时调用
     * 显示用户的详细信息
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result findOnlyUser(String openId) {
        userService.findByOpenId(openId);
        return Result.success();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findAll(Pageable pageable) {
        return Result.success(userService.findAll(pageable));
    }

    /**
     * 更新用户详细信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Result updateUser(@Valid User user) {
        try {
            new MobilePhoneNumber(user.getPhoneNumber()); // 校验手机号是否正确
        } catch (PhoneNumber.InvalidNumberException e) {
            e.printStackTrace();
        }
        userService.save(user);
        return Result.success(user);
    }

}


