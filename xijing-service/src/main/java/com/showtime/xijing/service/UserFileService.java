package com.showtime.xijing.service;

import com.showtime.xijing.entity.UserFile;
import com.showtime.xijing.repository.UserFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:41
 **/
@Service
public class UserFileService {

    private UserFileRepository userFileRepository;

    @Autowired
    public UserFileService(UserFileRepository userFileRepository) {
        this.userFileRepository = userFileRepository;
    }

    public UserFile save(UserFile userFile) {
        if (userFile.getId() != null) {
            userFile.setUpdateTime(new Date());
        } else {
            userFile.setCreateTime(new Date());
        }
        return userFileRepository.save(userFile);
    }
}
