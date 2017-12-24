package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.Tip;
import com.showtime.xijing.service.TipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by li on 2017/12/24.
 */
@Slf4j
@RestController
@RequestMapping(value = "/tip")
public class TipController {

    private TipService tipService;

    @Autowired
    public TipController(TipService tipService) {
        this.tipService = tipService;
    }

    @RequestMapping(value = "/tip", method = RequestMethod.GET)
    public Result updateUser(Tip tip) {
        tipService.save(tip);
        return Result.success();
    }

}
