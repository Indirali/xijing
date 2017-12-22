package com.showtime.xijing.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 七牛云 手写工具类
 * Created by kzyuan on 2017/6/20.
 */
public class QiniuUtil {

    private final static String ACCESS_KEY = "LuTPUHSJ8av5eC-ioHWy0GZ-qmtc0n3swMX-XgpM";
    private final static String SECRET_KEY = "R42FCMC99_OMQ_ybqT1d7pE0fI7FhvkWHEvQPM9z";

    /**
     * 空间默认域名
     */
    private static final String BUCKET_HOST_NAME = "http://p1cih3dw5.bkt.clouddn.com";

    private static UploadManager uploadManager = new UploadManager();

    private static int LIMIT_SIZE = 1000;

    /**
     * @return String[]
     * @throws QiniuException 七牛异常
     * @Description: 返回七牛帐号的所有空间
     */
    public static String[] listBucket() throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        return bucketManager.buckets();
    }

    /**
     * @param bucketName 空间名称
     * @param prefix     文件名前缀
     * @param limit      每次迭代的长度限制，最大1000，推荐值 100[即一个批次从七牛拉多少条]
     * @return List<FileInfo>
     * @Description: 获取指定空间下的文件列表
     */
    public static List<FileInfo> listFileOfBucket(String bucketName, String prefix, int limit) {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        BucketManager.FileListIterator it = bucketManager.createFileListIterator(bucketName, prefix, limit, null);
        List<FileInfo> list = new ArrayList<FileInfo>();
        while (it.hasNext()) {
            FileInfo[] items = it.next();
            if (null != items && items.length > 0) {
                list.addAll(Arrays.asList(items));
            }
        }
        return list;
    }

    /**
     * @param inputStream 待上传文件输入流
     * @param bucketName  空间名称
     * @param key         空间内文件的key
     * @param mimeType    文件的MIME类型，可选参数，不传入会自动判断
     * @return String
     * @throws IOException IO异常
     * @Description: 七牛图片上传
     */
    public static String uploadFile(InputStream inputStream, String bucketName, String key, String mimeType) throws IOException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(bucketName);
        byte[] byteData = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(byteData, key, token, null, mimeType, false);
        inputStream.close();
        return response.bodyString();
    }

    /**
     * @param inputStream 待上传文件输入流
     * @param bucketName  空间名称
     * @param key         空间内文件的key
     * @Description: 七牛图片上传
     */
    public static String uploadFile(InputStream inputStream, String bucketName, String key) throws IOException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(bucketName);
        byte[] byteData = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(byteData, key, token, null, null, false);
        inputStream.close();
        return response.bodyString();
    }


    /**
     * @param filePath   待上传文件的硬盘路径
     * @param fileName   待上传文件的文件名
     * @param bucketName 空间名称
     * @param key        空间内文件的key
     * @Description: 七牛图片上传
     */
    public static String uploadFile(String filePath, String fileName, String bucketName, String key) throws IOException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(bucketName);
        InputStream is = new FileInputStream(new File(filePath + fileName));
        byte[] byteData = IOUtils.toByteArray(is);
        Response response = uploadManager.put(byteData, (key == null || "".equals(key)) ? fileName : key, token);
        is.close();
        return response.bodyString();
    }

    /**
     * @param filePath   待上传文件的硬盘路径
     * @param fileName   待上传文件的文件名
     * @param bucketName 空间名称
     * @return String
     * @Description: 七牛图片上传[若没有指定文件的key, 则默认将fileName参数作为文件的key]
     */
    public static String uploadFile(String filePath, String fileName, String bucketName) throws IOException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(bucketName);
        InputStream is = new FileInputStream(new File(filePath + fileName));
        byte[] byteData = IOUtils.toByteArray(is);
        Response response = uploadManager.put(byteData, fileName, token);
        is.close();
        return response.bodyString();
    }


    /**
     * @param url        网络上一个资源文件的URL
     * @param bucketName 空间名称
     * @param key        空间内文件的key[唯一的]
     * @return String
     * @throws QiniuException
     * @Description: 提取网络资源并上传到七牛空间里
     */
    public static String fetchToBucket(String url, String bucketName, String key) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        DefaultPutRet putret = bucketManager.fetch(url, bucketName, key);
        return putret.key;
    }

    /**
     * @param url
     * @param bucketName
     * @return String
     * @Description: 提取网络资源并上传到七牛空间里, 不指定key，则默认使用url作为文件的key
     */
    public static String fetchToBucket(String url, String bucketName) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        DefaultPutRet putret = bucketManager.fetch(url, bucketName);
        return putret.key;
    }

    /**
     * @param bucket       源空间名称
     * @param key          源空间里文件的key(唯一的)
     * @param targetBucket 目标空间
     * @param targetKey    目标空间里文件的key(唯一的)
     * @return void
     * @throws QiniuException
     * @Description: 七牛空间内文件复制
     */
    public static void copyFile(String bucket, String key, String targetBucket, String targetKey) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.copy(bucket, key, targetBucket, targetKey);
    }


    /**
     * @param bucket       源空间名称
     * @param key          源空间里文件的key(唯一的)
     * @param targetBucket 目标空间
     * @param targetKey    目标空间里文件的key(唯一的)
     * @return void
     * @Description: 七牛空间内文件剪切
     */
    public static void moveFile(String bucket, String key, String targetBucket, String targetKey) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.move(bucket, key, targetBucket, targetKey);
    }

    /**
     * @param bucket
     * @param key
     * @param targetKey
     * @return void
     * @Description: 七牛空间内文件重命名
     */
    public static void renameFile(String bucket, String key, String targetKey) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.rename(bucket, key, targetKey);
    }

    /**
     * @param bucket 空间名称
     * @param key    空间内文件的key[唯一的]
     * @return void
     * @Description: 七牛空间内文件删除
     */
    public static void deleteFile(String bucket, String key) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        bucketManager.delete(bucket, key);
    }


    /**
     * @param bucketName 空间名称
     * @param prefix     文件key的前缀
     * @param limit      批量提取的最大数目
     * @return FileInfo[]
     * @throws QiniuException
     * @Description: 返回指定空间下的所有文件信息
     */
    public static FileInfo[] findFiles(String bucketName, String prefix, int limit) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, prefix, null, limit, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return listing.items;
    }


    /**
     * @param bucketName 空间名称
     * @param prefix     文件key的前缀
     * @return FileInfo[]
     * @Description: 返回指定空间下的所有文件信息
     */
    public static FileInfo[] findFiles(String bucketName, String prefix) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, prefix, null, LIMIT_SIZE, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return listing.items;
    }


    /**
     * @param bucketName
     * @return FileInfo[]
     * @Description: 返回指定空间下的所有文件信息
     */
    public static FileInfo[] findFiles(String bucketName) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, null, null, LIMIT_SIZE, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return listing.items;
    }


    /**
     * @param bucketName
     * @param key
     * @param limit
     * @return FileInfo
     * @throws QiniuException
     * @Description: 返回指定空间下的某个文件
     */
    public static FileInfo findOneFile(String bucketName, String key, int limit) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, key, null, limit, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return (listing.items)[0];
    }


    /**
     * @param bucketName
     * @param key
     * @Description: 返回指定空间下的某个文件(重载)
     */
    public static FileInfo findOneFile(String bucketName, String key) throws QiniuException {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth);
        FileListing listing = bucketManager.listFiles(bucketName, key, null, LIMIT_SIZE, null);
        if (listing == null || listing.items == null || listing.items.length <= 0) {
            return null;
        }
        return (listing.items)[0];
    }

    /**
     * @param key
     * @return String
     * @Description: 返回七牛空间内指定文件的访问URL
     */
    public static String getFileAccessUrl(String key) throws QiniuException {
        return BUCKET_HOST_NAME + "/" + key;
    }

}