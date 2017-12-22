package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.service.ConfirmService;
import com.showtime.xijing.service.ReportsService;
import com.showtime.xijing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    private ReportsService reportsService;

    @Autowired
    public ConfirmController(UserService userService,
                             ConfirmService confirmService,
                             ReportsService reportsService) {
        this.userService = userService;
        this.confirmService = confirmService;
        this.reportsService = reportsService;
    }

    @RequestMapping(value = "/allUser", method = RequestMethod.POST)
    public Result allFollowUser(String openId, String recruitInfoId) {
        // onfirmService.findAllByUser(userService.findOne(id));
        return Result.success();
    }


}
