package com.showtime.xijing.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛文件上传
 */
public class QiniuUpload {

    private final static String ACCESS_KEY = "W6HjfxWoDO24OOntKoPf75s5dq4CP96lcSmU9pxt";

    private final static String SECRET_KEY = "rebWZM-EPm1qoATTatoSQU5yzaVMs3NeLG3t4fA2";

    private final static String BUCKET = "dohkochouti";

    public static String qiniuUpload(String filePath, String fileName) {
        UploadManager uploadManager = new UploadManager();
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\"}");
        long expireSeconds = 3600;
        String token = auth.uploadToken(BUCKET, null, expireSeconds, putPolicy);
        try {
            Response response = uploadManager.put(filePath, fileName, token);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

}
