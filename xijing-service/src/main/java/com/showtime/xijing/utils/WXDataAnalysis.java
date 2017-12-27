package com.showtime.xijing.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/27
 * Time: 15:33
 **/
public class WXDataAnalysis {

    public static String analysisAsscessToken() {
        String rec = RequestAll.httpGet(WXRequestUtil.getAccessToken());
        JsonElement json = new JsonParser().parse(rec);
        String accessToken = json.getAsJsonObject().get("access_token").getAsString();
        return accessToken;
    }
}
