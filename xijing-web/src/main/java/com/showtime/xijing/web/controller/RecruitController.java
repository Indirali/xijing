package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.service.RecruitInfoService;
import com.showtime.xijing.service.RecruitService;
import com.showtime.xijing.service.ValidateService;
import com.showtime.xijing.web.vo.RecruitVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by li on 2017/12/9.
 */
@Slf4j
@RestController
@RequestMapping(value = "/recruit")
public class RecruitController {

    private RecruitService recruitService;
    private ValidateService validateService;
    private RecruitInfoService recruitInfoService;

    @Autowired
    public RecruitController(RecruitService recruitService,
                             ValidateService validateService,
                             RecruitInfoService recruitInfoService) {
        this.recruitService = recruitService;
        this.validateService = validateService;
        this.recruitInfoService = recruitInfoService;
    }

    /**
     * 新增/修改招聘信息
     *
     * @param recruitVo
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Result saveRecruit(RecruitVo recruitVo) {
        validateService.validateObject(recruitVo.getRecruit());
        validateService.validateObjectList(recruitVo.getRecruitInfos());
        recruitService.save(recruitVo.getRecruit());
        for (RecruitInfo recruitInfo : recruitVo.getRecruitInfos()) {
            recruitInfoService.save(recruitInfo);
        }
        return Result.success();
    }

    /**
     * 招聘详细信息
     *
     * @param recruitId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result findRecruitInfo(@Valid long recruitId) {
        return Result.success().data("recruit", recruitService.findById(recruitId))
                .data("recruitInfo", recruitInfoService.findByRecruitId(recruitId));
    }

}
