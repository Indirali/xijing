package com.showtime.xijing.aspect;

import com.showtime.xijing.annotation.UserAuth;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2018/1/17
 * Time: 16:28
 **/
@Aspect
public class UserAuthAspect {

    @Autowired
    private UserService userService;

    @Before("@annotation(userAuth)")
    public void limitMethod(UserAuth userAuth) throws Throwable {
        limit();
    }

    private void limit() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String openId = request.getParameter("openId");
        User user = userService.findByOpenId(openId);
        Assert.isTrue(user.isAuthStatus(), "用户未进行实名认证!");
    }

}
