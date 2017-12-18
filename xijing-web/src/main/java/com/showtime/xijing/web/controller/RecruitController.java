package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.RecruitCondition;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.RecruitInfoService;
import com.showtime.xijing.service.RecruitService;
import com.showtime.xijing.service.UserService;
import com.showtime.xijing.service.ValidateService;
import com.showtime.xijing.web.vo.RecruitVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
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
    private ValidateService validateService;
    private RecruitInfoService recruitInfoService;

    @Autowired
    public RecruitController(UserService userService,
                             RecruitService recruitService,
                             ValidateService validateService,
                             RecruitInfoService recruitInfoService) {
        this.userService = userService;
        this.recruitService = recruitService;
        this.validateService = validateService;
        this.recruitInfoService = recruitInfoService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findRecruitInfo(Pageable pageable,
                                  RecruitCondition recruitCondition) {
        return Result.success(recruitService.findAll(recruitCondition, pageable));
    }

    /**
     * 新增/修改招聘信息
     *
     * @param recruitVo
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Result saveRecruit(RecruitVo recruitVo, String openId) {
        User user = userService.findByOpenId(openId);
        Assert.notNull(user, "用户不存在！");
        if (user.getAuthStatus() == 0) {
            return Result.noAuth();
        }
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
    public Result findRecruitInfo(long recruitId) {
        return Result.success().data("recruit", recruitService.findById(recruitId))
                .data("recruitInfo", recruitInfoService.findByRecruitId(recruitId));
    }

}
