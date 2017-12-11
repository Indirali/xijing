package com.showtime.xijing.common.entity;

import org.springframework.util.Assert;

/**
 * 谷歌标准手机号码工具类
 */

public class MobilePhoneUtils {

    public static MobilePhoneNumber changeToMobilePhoneNumber(String phoneNumber) {
        // 手机号格式化为+86
        MobilePhoneNumber mpn = null;
        boolean success = false;
        try {
            mpn = new MobilePhoneNumber(phoneNumber);
            success = true;
        } catch (PhoneNumber.InvalidNumberException e) {
            Assert.isTrue(success, "手机号不合法");
        }
        return mpn;

    }
}
