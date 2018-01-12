package com.showtime.xijing.web.interceptor;

import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2018/1/12
 * Time: 15:40
 **/
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String openId = request.getParameter("openId");
        User user = userService.findByOpenId(openId);
        Assert.isTrue(user.getAuthStatus() == 1, "用户未进行实名认证!");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
