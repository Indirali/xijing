package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.common.utils.DateUtil;
import com.showtime.xijing.entity.Confirm;
import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.ConfirmService;
import com.showtime.xijing.service.RecruitService;
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
    private RecruitService recruitService;
    private ConfirmService confirmService;

    @Autowired
    public ConfirmController(UserService userService,
                             RecruitService recruitService,
                             ConfirmService confirmService) {
        this.userService = userService;
        this.recruitService = recruitService;
        this.confirmService = confirmService;
    }

    @RequestMapping(value = "/userConfirm", method = RequestMethod.GET)
    public Result userConfirm(Confirm confirm) {
        User user = userService.findOne(confirm.getUser().getId());
        Recruit recruit = recruitService.findById(confirm.getRecruit().getId());
        Date date = recruit.getParticipationTime();
        Assert.notNull(confirmService.findByUserAndCreateTimeBetween(user, DateUtil.StartTime(date), DateUtil.EndTime(date)), "当天已有行程，不能再进行确认");
        confirmService.save(confirm);
        return Result.success();
    }

    @RequestMapping(value = "/userSchedule", method = RequestMethod.GET)
    public Result userSchedule(long userId) {
        User user = userService.findOne(userId);
        return Result.success(confirmService.findByUser(user));
    }

}
