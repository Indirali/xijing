package com.showtime.xijing.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/27
 * Time: 12:51
 **/
@Slf4j
public class RequestAll {

    /**
     * 通过HttpGet类发送GET请求并获取返回信息
     *
     * @param path 发送至的网址
     * @return
     */
    public static String httpGet(String path) {
        if (path == null) {
            return null;
        }
        String rec = null;
        HttpGet get = new HttpGet(path);
        try {
            HttpResponse response = HttpClients.createDefault().execute(get);
            HttpEntity entity = response.getEntity();
            rec = EntityUtils.toString(entity);
        } catch (IOException e) {
            log.debug(e.toString());
            e.printStackTrace();
        }
        return rec;
    }

    /**
     * 通过HttpPost类发送Post请求并获取返回信息
     *
     * @param path 发送至的网址
     * @return
     */
    public static String httpPost(String path, Map<String, String> params) {
        if (path == null) {
            return null;
        }
        String rec = null;
        HttpPost post = new HttpPost(path);
        try {
            if (null != params) {
                List<NameValuePair> nvps = new ArrayList<>();
                for (String name : params.keySet()) {
                    nvps.add(new BasicNameValuePair(name, params.get(name)));
                }
                post.setEntity(new UrlEncodedFormEntity(nvps));
            }
            HttpResponse response = HttpClients.createDefault().execute(post);
            HttpEntity entity = response.getEntity();
            rec = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rec;
    }

}
