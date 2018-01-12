package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.Follows;
import com.showtime.xijing.entity.Recruit;
import com.showtime.xijing.service.FollowsService;
import com.showtime.xijing.service.RecruitService;
import com.showtime.xijing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/20
 * Time: 16:44
 **/
@Slf4j
@RestController
@RequestMapping(value = "/follow")
public class FollowsController {

    private UserService userService;
    private RecruitService recruitService;
    private FollowsService followsService;

    @Autowired
    public FollowsController(UserService userService,
                             RecruitService recruitService,
                             FollowsService followsService) {
        this.userService = userService;
        this.recruitService = recruitService;
        this.followsService = followsService;
    }

    @RequestMapping(value = "/allUser", method = RequestMethod.POST)
    public Result allFollowUser(long id) {
        followsService.findAllByUser(userService.findOne(id));
        return Result.success();
    }

    @RequestMapping(value = "/allRecruits", method = RequestMethod.POST)
    public Result allFollowRecruit(long id) {
        List<Follows> follows = followsService.findAllByUser(userService.findOne(id));
        List<Recruit> recruits = new ArrayList<>();
        for (Follows follow : follows) {
            recruits.addAll(recruitService.findByUser(follow.getFollowUser().getId()));
        }
        return Result.success();
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public Result followUser(Follows follows) {
        followsService.save(follows);
        return Result.success();
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public Result cancelFollows(long id) {
        followsService.deleteFollow(id);
        return Result.success();
    }

}
