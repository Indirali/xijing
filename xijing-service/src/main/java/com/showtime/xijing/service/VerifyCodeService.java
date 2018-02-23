package com.showtime.xijing.service;


import com.showtime.xijing.common.entity.MobilePhoneNumber;
import com.showtime.xijing.enums.VerifyCodeType;
import com.showtime.xijing.utils.WXDataAnalysis;
import com.yunpian.sdk.YunpianException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.showtime.xijing.enums.VerifyCodeType.Verify_Code_Access_Token;
import static com.showtime.xijing.enums.VerifyCodeType.Verify_Code_Count;

@Slf4j
@Service
public class VerifyCodeService {

    private StringRedisTemplate stringRedisTemplate;
    private SMSVerifyCodeService smsVerifyCodeService;
    private static final Random random = new Random();

    @Autowired
    public VerifyCodeService(StringRedisTemplate stringRedisTemplate, SMSVerifyCodeService smsVerifyCodeService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.smsVerifyCodeService = smsVerifyCodeService;
    }

    /**
     * 获取手机号验证码
     *
     * @param mpn
     * @param type
     */
    public void getVerifyCode(MobilePhoneNumber mpn, VerifyCodeType type) {
        Assert.notNull(type, "短信验证码类型不可为空");
        String phoneNumber = Long.toString(mpn.getNationalNumber());
        Assert.isTrue(stringRedisTemplate.boundValueOps(Verify_Code_Count + phoneNumber).get() == null, "未满一分钟，不能重复发送！");
        stringRedisTemplate.boundValueOps(Verify_Code_Count + phoneNumber).set("1", 60L, TimeUnit.SECONDS);
        String code = String.valueOf(random.nextInt(1000000));
        stringRedisTemplate.boundListOps(type + phoneNumber).leftPush(code);
        try {
            smsVerifyCodeService.sendSms(phoneNumber, code);
        } catch (IOException e) {
            log.debug(e.toString());
            e.printStackTrace();
        } catch (YunpianException e) {
            e.printStackTrace();
            log.info("短信发送异常：" + e.getMessage());
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
        deleteVerifyCodeCache(phoneNumber, type);
    }

    /**
     * 清除缓存
     *
     * @param phoneNumber
     * @param type
     */
    private void deleteVerifyCodeCache(String phoneNumber, VerifyCodeType type) {
        Assert.notNull(type, "短信验证码类型不可为空");
        stringRedisTemplate.delete(type + phoneNumber);
        stringRedisTemplate.delete(Verify_Code_Count + phoneNumber);
    }

    /**
     * 获取微信Access_Token
     *
     * @return
     */
    public String getWXAccessTokenCache() {
        if (!stringRedisTemplate.hasKey(Verify_Code_Access_Token.toString())) {
            stringRedisTemplate.boundValueOps(Verify_Code_Access_Token.toString()).set(WXDataAnalysis.analysisAsscessToken(), 7200L, TimeUnit.SECONDS);
        }
        return stringRedisTemplate.boundValueOps(Verify_Code_Access_Token.toString()).get();
    }


}
