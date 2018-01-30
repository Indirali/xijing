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

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private UserService userService;

    @Autowired
    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String openId = request.getParameter("openId");
        User user = userService.findByOpenId(openId);
        Assert.notNull(user, "用户不存在,如有问题请在公众号【戏鲸汇演】留言!");
        Assert.isTrue(user.getCreditDegree() > 300, "用户信用值过低,不能进行任何操作,如有问题请在公众号【戏鲸汇演】留言!");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
