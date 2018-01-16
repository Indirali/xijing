package com.showtime.xijing.service;

import com.showtime.xijing.entity.Follows;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.repository.FollowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Follows> findAllByUser(User user) {
        return followsRepository.findAllByUserAndStatus(user, 0);
    }

    public void deleteFollow(long id) {
        followsRepository.delete(id);
    }

    public Follows save(Follows follows) {
        return followsRepository.save(follows);
    }

}
