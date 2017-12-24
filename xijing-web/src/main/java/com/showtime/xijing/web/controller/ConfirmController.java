package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.Confirm;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.ConfirmService;
import com.showtime.xijing.service.ReportsService;
import com.showtime.xijing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/22
 * Time: 11:31
 **/
@Slf4j
@RestController
@RequestMapping(value = "/confirm")
public class ConfirmController {

    private UserService userService;
    private ConfirmService confirmService;

    @Autowired
    public ConfirmController(UserService userService,
                             ConfirmService confirmService) {
        this.userService = userService;
        this.confirmService = confirmService;
    }

    @RequestMapping(value = "/userConfirm", method = RequestMethod.GET)
    public Result userConfirm(Confirm confirm, Date date) {
        User user = userService.findOne(confirm.getUser().getId());
        Assert.notNull(confirmService.findByUserAndCreateTime(user, date), "当天已有行程，不能再进行确认");
        confirmService.save(confirm);
        return Result.success();
    }

    @RequestMapping(value = "/userSchedule", method = RequestMethod.GET)
    public Result userSchedule(long userId) {
        User user = userService.findOne(userId);
        return Result.success(confirmService.findByUser(user));
    }

}
