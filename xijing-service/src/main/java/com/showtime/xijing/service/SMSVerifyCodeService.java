package com.showtime.xijing.service;

import com.yunpian.sdk.YunpianException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.yunpian.sdk.util.HttpUtil.post;

@Service
public class SMSVerifyCodeService {

    private static final String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";
    private static final String API_KEY = "7936e927f6e6d9b91593722fbc05b548";
    private static final String YUN_PIAN_VERIFYTEXT = "【戏鲸汇演】 您的验证码是";
    private static final String YUN_PIAN_POSTFIX = "(5分钟之内有效,如非本人操作,请忽略)";

    public String sendSms(String mobile, String code) throws IOException, YunpianException {
        Map<String, String> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put("text", YUN_PIAN_VERIFYTEXT + code + YUN_PIAN_POSTFIX);
        params.put("mobile", mobile);
        return post(URI_SEND_SMS, params);
    }
}
