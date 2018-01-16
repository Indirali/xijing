package com.showtime.xijing.admin.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/13
 * Time: 12:23
 **/
@Slf4j
@RestController
@RequestMapping(value = "/admin/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取全部用户
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findAll(Pageable pageable) {
        return Result.success(userService.findAllUser(pageable));
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateUser(User user) {
        userService.save(user);
        return Result.success();
    }


}
