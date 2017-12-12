package com.showtime.xijing.web.controller;

import com.showtime.xijing.service.ReportsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private ReportsService reportsService;

    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

}
