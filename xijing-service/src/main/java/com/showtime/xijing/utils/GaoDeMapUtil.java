package com.showtime.xijing.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/25
 * Time: 11:43
 **/
@Slf4j
public class GaoDeMapUtil {

    // 高德应用的地址
    private static String GAODEAPPID = "06f40c0ff20d1ab1cbbd78013b933efe";

    // 地理编码地址
    private static String MAP_CODEURL = "http://restapi.amap.com/v3/geocode/regeo?";

    /**
     * 获取国家省市
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    public static String getLocation(String longitude, String latitude) {
        String url = MAP_CODEURL;
        String location = longitude + "," + latitude;
        String params = "key=" + GAODEAPPID + "&location=" + location;
        String rec = RequestAll.httpGet(url + params);
        JsonElement json = new JsonParser().parse(rec);  // 使用json类解析结果
        JsonElement jsonObject = json.getAsJsonObject().get("regeocode").getAsJsonObject().get("addressComponent");
        String country = jsonObject.getAsJsonObject().get("country").getAsString();
        String province = jsonObject.getAsJsonObject().get("province").getAsString();
        String city = jsonObject.getAsJsonObject().get("city").toString();
        String place = country + "-" + province;
        if (city.length() != 2) {
            place += "-" + city.replace("\"", "");
        }
        return place;
    }

}
