package com.showtime.xijing.aspect;

import com.showtime.xijing.annotation.UserAuth;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.service.UserService;
import org.apache.http.HttpRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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

    public UserAuthAspect() {
    }

    @Around("@annotation(userAuth)")
    public void limitMethod(ProceedingJoinPoint pjp, UserAuth userAuth) throws Throwable {
        limit(pjp);
    }

    @Around("@within(userAuth)")
    public void limitType(ProceedingJoinPoint pjp, UserAuth userAuth) throws Throwable {
        limit(pjp);
    }

    private void limit(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        HttpServletRequest request = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpRequest) {
                request = (HttpServletRequest) args[i];
                break;
            }
        }
        if (request == null) {
            try {
                throw new Exception("方法中缺失HttpServletRequest参数");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String openId = request.getParameter("openId");
        User user = userService.findByOpenId(openId);
        Assert.isTrue(user.isAuthStatus(), "用户未进行实名认证!");
    }


}
