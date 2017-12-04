package com.showtime.xijing.service;

import com.showtime.xijing.entity.User;
import com.showtime.xijing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/4
 * Time: 15:41
 **/
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
