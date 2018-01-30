package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.RecruitInfo;
import com.showtime.xijing.entity.Reports;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.RecruitInfoService;
import com.showtime.xijing.service.ReportsService;
import com.showtime.xijing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/12
 * Time: 11:49
 **/
@Slf4j
@RestController
@RequestMapping(value = "/report")
public class ReportsController {

    private UserService userService;
    private ReportsService reportsService;
    private RecruitInfoService recruitInfoService;

    @Autowired
    public ReportsController(UserService userService,
                             ReportsService reportsService,
                             RecruitInfoService recruitInfoService) {
        this.userService = userService;
        this.reportsService = reportsService;
        this.recruitInfoService = recruitInfoService;
    }

    /**
     * 用户报名活动
     *
     * @param reports
     * @return
     */
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public Result reportRecruit(Reports reports) {
        User user = userService.findUserById(reports.getUser().getId());
        if (user.getPhoneNumber() == null || user.getWxNumber() == null || user.getWxImage() == null) {
            return Result.noInformation();
        }
        reportsService.save(reports);
        return Result.success();
    }

    /**
     * 用户的报名列表
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/userReport", method = RequestMethod.GET)
    public Result userReport(String openId) {
        User user = userService.findByOpenId(openId);
        return Result.success(reportsService.findAllByUser(user));
    }

    /**
     * 取消报名
     *
     * @param reportId
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public Result cancelReport(long reportId) {
        Reports reports = reportsService.findReportById(reportId);
        reports.setStatus(3);
        return Result.success();
    }

    /**
     * 用户被喜欢
     *
     * @param reportId
     * @return
     */
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public Result likeReportUser(long reportId) {
        Reports reports = reportsService.findReportById(reportId);
        reports.setStatus(1);
        reportsService.save(reports);
        return Result.success();
    }

    /**
     * 用户进入待选
     *
     * @param reportId
     * @return
     */
    @RequestMapping(value = "/dislike", method = RequestMethod.GET)
    public Result dislikeReportUser(long reportId) {
        Reports reports = reportsService.findReportById(reportId);
        reports.setStatus(2);
        reportsService.save(reports);
        return Result.success();
    }

    /**
     * 进行筛选
     *
     * @param recruitInfoId
     * @return
     */
    @RequestMapping(value = "/filtrate", method = RequestMethod.GET)
    public Result filtrateReportUser(long recruitInfoId) {
        RecruitInfo recruitInfo = recruitInfoService.findRecruitInfoById(recruitInfoId);
        List<Reports> unDoReports = reportsService.findByReportRecruitInfoAndStatus(recruitInfo, 0);
        List<Reports> likeReports = reportsService.findByReportRecruitInfoAndStatus(recruitInfo, 1);
        List<Reports> dislikeReports = reportsService.findByReportRecruitInfoAndStatus(recruitInfo, 2);
        return Result.success()
                .data("unDo", unDoReports)
                .data("unDoCount", unDoReports.size())
                .data("like", likeReports)
                .data("likeCount", likeReports.size())
                .data("dislike", dislikeReports)
                .data("dislikeCount", dislikeReports.size());
    }

}
