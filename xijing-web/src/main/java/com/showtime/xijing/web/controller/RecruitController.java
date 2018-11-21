package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.RecruitService;
import com.showtime.xijing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by li on 2017/12/9.
 */
@Slf4j
@RestController
@RequestMapping(value = "/recruit")
public class RecruitController {

    private UserService userService;
    private RecruitService recruitService;

    @Autowired
    public RecruitController(UserService userService,
                             RecruitService recruitService) {
        this.userService = userService;
        this.recruitService = recruitService;
    }

    /**
     * 获取全部招聘信息
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findRecruitInfo(Pageable pageable) {
        return Result.success(recruitService.findAll(pageable));
    }


    /**
     * 招聘详细信息
     *
     * @param recruitId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result findRecruitInfo(long recruitId) {
        return Result.success(recruitService.findRecruitById(recruitId));
    }

    /**
     * 用户发布的招聘
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/userRecruit", method = RequestMethod.GET)
    public Result userRecruit(String openId) {
        User user = userService.findByOpenId(openId);
        return Result.success(recruitService.findByUser(user));
    }

}
