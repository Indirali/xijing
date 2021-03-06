package com.showtime.xijing.admin.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.service.RecruitInfoService;
import com.showtime.xijing.service.RecruitService;
import com.showtime.xijing.service.ValidateService;
import com.showtime.xijing.vo.RecruitVo;
import lombok.extern.slf4j.Slf4j;
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
    private ValidateService validateService;
    private RecruitInfoService recruitInfoService;

    public RecruitController(RecruitService recruitService,
                             ValidateService validateService,
                             RecruitInfoService recruitInfoService) {
        this.recruitService = recruitService;
        this.validateService = validateService;
        this.recruitInfoService = recruitInfoService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findRecruitInfo(Pageable pageable) {
        return Result.success(recruitService.findAll(pageable));
    }

    /**
     * 新增/修改招聘信息
     *
     * @param recruitVo
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveRecruit(RecruitVo recruitVo) {
        validateService.validateObject(recruitVo.getRecruit());
        validateService.validateObjectList(recruitVo.getRecruitInfos());
        Long[] recruitInfoIds = new Long[recruitVo.getRecruitInfos().size()];
        for (int i = 0; i < recruitVo.getRecruitInfos().size(); i++) {
            RecruitInfo base = recruitInfoService.save(recruitVo.getRecruitInfos().get(i));
            recruitInfoIds[i] = base.getId();
        }
        Recruit recruit = recruitVo.getRecruit();
        recruit.setRecruitInfos(recruitInfoService.findByIdIn(recruitInfoIds));
        recruitService.save(recruit);
        return Result.success();
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
