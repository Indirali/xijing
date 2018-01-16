package com.showtime.xijing.admin.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitCondition;
import com.showtime.xijing.service.RecruitService;
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
@RequestMapping(value = "/admin/recruit")
public class RecruitController {

    private RecruitService recruitService;

    @Autowired
    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findRecruitInfo(Pageable pageable,
                                  RecruitCondition recruitCondition) {
        return Result.success(recruitService.findAll(recruitCondition, pageable));
    }

    /**
     * 修改招聘信息
     *
     * @param recruit
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result saveRecruit(Recruit recruit) {
        recruitService.save(recruit);
        return Result.success();
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

}
