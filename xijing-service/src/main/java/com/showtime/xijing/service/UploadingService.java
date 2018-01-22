package com.showtime.xijing.service;

import com.showtime.xijing.entity.UserFile;
import com.showtime.xijing.repository.UserFileRepository;
import com.showtime.xijing.utils.QiniuUtil;
import com.showtime.xijing.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.showtime.xijing.enums.UploadType.Image;
import static com.showtime.xijing.enums.UploadType.Video;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/22
 * Time: 15:40
 **/
@Slf4j
@Service
public class UploadingService {

    //默认上传空间
    private final static String BUCKET_NAME = "xijing";
    private UserFileRepository userFileRepository;

    @Autowired
    public UploadingService(UserFileRepository userFileRepository) {
        this.userFileRepository = userFileRepository;
    }

    /**
     * 图片上传
     *
     * @param mf   图片流
     * @param type 类型
     * @return
     * @throws IOException io异常
     */
    public UserFile uploadingImage(MultipartFile mf, boolean type) throws IOException {
        papers(mf, type);
        String filename = RandomUtil.getRandomFileName();
        String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".") + 1, mf.getOriginalFilename().length()).toLowerCase();
        String url = QiniuUtil.getFileAccessUrl(filename);
        UserFile file = new UserFile();
        file.setFileName(filename);
        file.setFormat(ext);
        file.setType(type ? Image : Video);
        file.setUrl(url);
        QiniuUtil.uploadFile(mf.getInputStream(), BUCKET_NAME, filename);
        return userFileRepository.save(file);
    }

    /**
     * 对上传的文件大小进行判断
     *
     * @param file 图片流
     * @param type 类型
     */
    private void papers(MultipartFile file, boolean type) {
        Assert.notNull(file, "文件为空");
        log.info("上传文件大小是：" + file.getSize());
        if (type) {
            Assert.isTrue(file.getSize() < 5 * 1024 * 1024, "图片大小超过5M!");
        } else {
            Assert.isTrue(file.getSize() < 20 * 1024 * 1024, "视频大小超过20M!");
        }
        List<String> fileTypes = new ArrayList<>();
        if (type) {
            fileTypes.add("jpg");
            fileTypes.add("jpeg");
            fileTypes.add("png");
        } else {
            fileTypes.add(".wma");
            fileTypes.add(".avi");
            fileTypes.add(".rm");
            fileTypes.add(".mpg");
            fileTypes.add(".mp4");
            fileTypes.add(".mov");
        }
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        Assert.isTrue(fileTypes.contains(ext), "文件格式错误");
    }

}
