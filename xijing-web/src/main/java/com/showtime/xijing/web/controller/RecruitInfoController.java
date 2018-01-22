package com.showtime.xijing.web.controller;

import com.showtime.xijing.annotation.UserAuth;
import com.showtime.xijing.common.Result;
import com.showtime.xijing.service.RecruitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/11
 * Time: 16:57
 **/
@Slf4j
@RestController
@RequestMapping(value = "/recruitInfo")
public class RecruitInfoController {

    private RecruitInfoService recruitInfoService;

    @Autowired
    public RecruitInfoController(RecruitInfoService recruitInfoService) {
        this.recruitInfoService = recruitInfoService;
    }

    /**
     * 删除招聘详情
     *
     * @param recruitInfoId
     * @return
     */
    @UserAuth
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result saveRecruit(long recruitInfoId) {
        recruitInfoService.delete(recruitInfoId);
        return Result.success();
    }

}
