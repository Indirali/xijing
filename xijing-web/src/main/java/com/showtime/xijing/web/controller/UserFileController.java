package com.showtime.xijing.web.controller;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.entity.UserFile;
import com.showtime.xijing.service.UploadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/22
 * Time: 11:00
 **/
@Slf4j
@RestController
@RequestMapping(value = "/userFile")
public class UserFileController {

    private UploadingService uploadingImage;

    @Autowired
    public UserFileController(UploadingService uploadingImage) {
        this.uploadingImage = uploadingImage;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result findOnlyUser(MultipartFile mf, boolean type) {
        UserFile userFile;
        try {
            userFile = uploadingImage.uploadingImage(mf, type);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("图片上传失败：" + e.getMessage());
            return Result.fail("图片上传失败！");
        }
        return Result.success(userFile);
    }

}
