package com.showtime.xijing.service;

import com.showtime.xijing.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/14
 * Time: 16:40
 **/
@Service
public class UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

}
