package com.showtime.xijing.service;


import com.showtime.xijing.common.entity.MobilePhoneNumber;
import com.showtime.xijing.enums.VerifyCodeType;
import com.yunpian.sdk.YunpianException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyCodeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SMSVerifyCodeService smsVerifyCodeService;
    private static final Random random = new Random();

    /**
     * 获取手机号验证码
     *
     * @param mpn
     * @param type
     */
    public void getVerifyCode(MobilePhoneNumber mpn, VerifyCodeType type) {
        Assert.notNull(type, "短信验证码类型不可为空");
        String phoneNumber = Long.toString(mpn.getNationalNumber());
        Assert.isTrue(stringRedisTemplate.boundValueOps(VerifyCodeType.VERIFY_COUNT + phoneNumber).get() == null, "未满一分钟，不能重复发送！");
        stringRedisTemplate.boundValueOps(VerifyCodeType.VERIFY_COUNT + phoneNumber).set("1", 60L, TimeUnit.SECONDS);
        String code = String.valueOf(random.nextInt(1000000));
        stringRedisTemplate.boundListOps(type + phoneNumber).leftPush(code);
        try {
            smsVerifyCodeService.sendSms(phoneNumber, code);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (YunpianException e) {
            Assert.isNull(e, e.getMessage());
        }
    }

    /**
     * 校验手机验证码。如果成功则删除缓存
     *
     * @param phoneNumber
     * @param code
     * @param type
     */
    public void detectVerifyCode(String phoneNumber, String code, VerifyCodeType type) {
        Assert.notNull(type, "短信验证码类型不可为空");
        Assert.isTrue(stringRedisTemplate.boundListOps(type + phoneNumber).index(0L) != null, "请先获取验证码！");
        Assert.isTrue(stringRedisTemplate.boundListOps(type + phoneNumber).index(0L).equals(code), "验证码错误，请重新输入！");
        deleteVerifyCodeCache(phoneNumber, code, type);
    }

    /**
     * 清除缓存
     *
     * @param phoneNumber
     * @param code
     * @param type
     */
    public void deleteVerifyCodeCache(String phoneNumber, String code, VerifyCodeType type) {
        Assert.notNull(type, "短信验证码类型不可为空");
        stringRedisTemplate.delete(type + phoneNumber);
        stringRedisTemplate.delete(VerifyCodeType.VERIFY_COUNT + phoneNumber);
    }

}
