package com.showtime.xijing.service;

import com.showtime.xijing.entity.User;
import com.showtime.xijing.entity.UserInfo;
import com.showtime.xijing.enums.PushType;
import com.showtime.xijing.repository.UserInfoRepository;
import com.showtime.xijing.repository.UserRepository;
import com.showtime.xijing.utils.WXRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.annotation.JmsListener;
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
    private VerifyCodeService verifyCodeService;
    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       VerifyCodeService verifyCodeService,
                       UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.verifyCodeService = verifyCodeService;
        this.userInfoRepository = userInfoRepository;
    }

    @Cacheable(value = "findUserById")
    public User findUserById(long id) {
        return userRepository.findOne(id);
    }

    @Cacheable(value = "findByOpenId")
    public User findByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

    @Cacheable(value = "findAllUser")
    public Page<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @CachePut(value = {"findAllUser", "findUserById", "findByOpenId"})
    public User save(User user) {
        if (user.getId() != null) {
            user.setUpdateTime(new Date());
        } else {
            user.setCreateTime(new Date());
        }
        return userRepository.save(user);
    }

    @Transactional
    @CachePut(value = {"findAllUser", "findUserById", "findByOpenId"})
    public void updateUserInfo(User user, UserInfo userInfo) {
        userInfo.setCreateTime(new Date());
        UserInfo info = userInfoRepository.save(userInfo);
        user.setCreateTime(new Date());
        user.setUserInfo(info);
        userRepository.save(user);
    }

    @JmsListener(destination = "userQueue")
    public void receiveQueue(PushType pushType) {
        String accessToken = verifyCodeService.getWXAccessTokenCache();
        if (pushType == PushType.Recruit_Fail) {
            WXRequestUtil.sendNotification(accessToken);
        }
    }

}
