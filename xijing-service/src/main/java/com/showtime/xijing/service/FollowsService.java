package com.showtime.xijing.service;

import com.showtime.xijing.entity.Follows;
import com.showtime.xijing.repository.FollowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:36
 **/
@Service
public class FollowsService {

    private FollowsRepository followsRepository;

    @Autowired
    public FollowsService(FollowsRepository followsRepository) {
        this.followsRepository = followsRepository;
    }

    public Follows save(Follows follows) {
        if (follows.getId() != null) {
            follows.setUpdateTime(new Date());
        } else {
            follows.setCreateTime(new Date());
        }
        return followsRepository.save(follows);
    }

}
