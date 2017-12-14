package com.showtime.xijing.service;

import com.showtime.xijing.entity.User;
import com.showtime.xijing.entity.UserInfo;
import com.showtime.xijing.repository.UserInfoRepository;
import com.showtime.xijing.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:41
 **/
@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public User findByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User save(User user) {
        if (user.getId() != null) {
            user.setUpdateTime(new Date());
        } else {
            user.setCreateTime(new Date());
        }
        return userRepository.save(user);
    }

    @Transactional
    public void updateUserInfo(User user, UserInfo userInfo) {
        userInfo.setCreateTime(new Date());
        UserInfo info = userInfoRepository.save(userInfo);
        user.setCreateTime(new Date());
        user.setUserInfo(info);
        userRepository.save(user);
    }

}
