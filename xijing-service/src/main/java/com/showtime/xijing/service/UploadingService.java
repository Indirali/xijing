package com.showtime.xijing.service;

import com.showtime.xijing.entity.UserFile;
import com.showtime.xijing.repository.UserFileRepository;
import com.showtime.xijing.utils.QiniuUtil;
import com.showtime.xijing.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/22
 * Time: 15:40
 **/
@Slf4j
@Service
public class UploadingService {

    /**
     * 默认上传空间
     */
    private final static String BUCKET_NAME = "xijing";

    private UserFileRepository userFileRepository;

    @Autowired
    public UploadingService(UserFileRepository userFileRepository) {
        this.userFileRepository = userFileRepository;
    }

    /**
     * @param mf 图片流
     */
    public UserFile uploadingImage(MultipartFile mf, String type) throws IOException {
        String filename = RandomUtil.getRandomFileName();
        String fileName = mf.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        String url = QiniuUtil.getFileAccessUrl(fileName);
        UserFile file = new UserFile();
        file.setFileName(fileName);
        file.setFormat(ext);
        file.setType(type);
        QiniuUtil.uploadFile(mf.getInputStream(), BUCKET_NAME, filename);
        return userFileRepository.save(file);
    }


    /**
     * 对上传的图片大小进行判断
     *
     * @param imgFile 图片流
     */

    public boolean papers(MultipartFile imgFile) {
        if (imgFile == null) {
            log.info("上传图片大小是：" + imgFile.getSize());
            if (imgFile.getSize() < 10485760) {
                List<String> fileTypes = new ArrayList<String>();
                fileTypes.add("jpg");
                fileTypes.add("jpeg");
                fileTypes.add("png");
                String fileName = imgFile.getOriginalFilename();
                //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                //对扩展名进行小写转换
                ext = ext.toLowerCase();
                if (fileTypes.contains(ext)) { //如果扩展名属于允许上传的类型，则创建文件
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
