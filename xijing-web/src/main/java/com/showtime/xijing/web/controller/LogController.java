package com.showtime.xijing.web.controller;


import com.showtime.xijing.web.service.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Jag 2017/11/6 17:07
 */
@RestController
@RequestMapping("log")
public class LogController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("writelog")
    public Object writeLog() {
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        new LogHelper().helpMethod();
        return "...Look at your idea Console please...";
    }
}