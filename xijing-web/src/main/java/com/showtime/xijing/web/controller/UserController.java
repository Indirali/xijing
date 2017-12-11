package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.common.entity.MobilePhoneNumber;
import com.showtime.xijing.common.entity.MobilePhoneUtils;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.enums.VerifyCodeType;
import com.showtime.xijing.service.UserService;
import com.showtime.xijing.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.showtime.xijing.enums.VerifyCodeType.VERIFY_CODE_CHANGE_PHONE;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 16:05
 **/
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;
    private VerifyCodeService verifyCodeService;

    @Autowired
    private UserController(UserService userService,
                           VerifyCodeService verifyCodeService) {
        this.userService = userService;
        this.verifyCodeService = verifyCodeService;
    }

    /**
     * 进入用户主页面时调用
     * 显示用户的详细信息
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result findOnlyUser(String openId) {
        return Result.success(userService.findByOpenId(openId));
    }

    /**
     * 获取全部用户
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findAll(Pageable pageable) {
        return Result.success(userService.findAll(pageable));
    }

    /**
     * 更新用户手机号
     *
     * @param phoneNumber
     * @return
     */
    @RequestMapping(value = "/changePhoneNumber", method = RequestMethod.GET)
    public Result updateUser(String phoneNumber, String code, String openId) {
        User user = userService.findByOpenId(openId);
        Assert.notNull(user, "用户不存在!");
        verifyCodeService.detectVerifyCode(phoneNumber, code, VERIFY_CODE_CHANGE_PHONE);
        user.setPhoneNumber(phoneNumber);
        userService.save(user);
        return Result.success();
    }

    /**
     * 发送验证码
     *
     * @param phoneNumber
     * @param openId
     * @param verifyCodeType
     * @return
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
    public Result verify(String phoneNumber, String openId, VerifyCodeType verifyCodeType) {
        User user = userService.findByOpenId(openId);
        Assert.notNull(user, "用户不存在!");
        MobilePhoneNumber mpn = MobilePhoneUtils.changeToMobilePhoneNumber(phoneNumber);
        verifyCodeService.getVerifyCode(mpn, verifyCodeType);
        return Result.success();
    }

}


