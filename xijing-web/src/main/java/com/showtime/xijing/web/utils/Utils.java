package com.showtime.xijing.web.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*******************************************************************************
 * Copyright (c) 2005-2017 longDai, Inc.
 *
 * Contributors:
 *******************************************************************************/
public class Utils {
    /**
     * 正则表达式：手机号
     */
    private static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 验证手机号
     */
    public static Boolean checkPhone(String phone) {
        Pattern p = Pattern.compile(REGEX_MOBILE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }


    /**
     * 效验 map 里是否有空
     */
    public static Boolean checkMapNull(Map<String, Object> paramMap) {
        for (Object values : paramMap.values()) {
            if (values == null) {
                return false;
            }
        }
        return true;
    }


}
