package com.showtime.xijing.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 18:16
 **/
@Slf4j
public class WXRequestUtil {

    private static final String appId = "wx4245c40948b87448";
    private static final String appSecret = "89614c40db4706335b5438f808911633";

    // 获取code的请求地址
    private static final String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";

    // 获取code的请求地址
    private static final String Get_Access_Token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    // 拉取用户信息的请求地址
    private static final String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    // 获取Web_access_token的请求地址
    private static final String Web_Access_Token = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    // 发送通知请求地址
    private static final String Send_Notification = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";

    //替换字符串
    public static String getCode(String APPID, String REDIRECT_URI, String SCOPE) {
        return String.format(Get_Code, APPID, REDIRECT_URI, SCOPE);
    }

    //替换字符串
    public static String getAccessToken() {
        return String.format(Get_Access_Token, appId, appSecret);
    }

    //替换字符串
    public static String getUserMessage(String access_token, String openid) {
        return String.format(User_Message, access_token, openid);
    }

    //替换字符串
    public static String getWebAccess(String CODE) {
        return String.format(Web_Access_Token, appId, appSecret, CODE);
    }

    //替换字符串
    public static String sendNotification(String accessToken) {
        return String.format(Send_Notification, accessToken);
    }

}
