package com.showtime.xijing.service;

import com.showtime.xijing.entity.Confirm;
import com.showtime.xijing.entity.User;
import com.showtime.xijing.repository.ConfirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/8
 * Time: 12:34
 **/
@Service
public class ConfirmService {

    private ConfirmRepository confirmRepository;

    @Autowired
    public ConfirmService(ConfirmRepository confirmRepository) {
        this.confirmRepository = confirmRepository;
    }

    public Confirm save(Confirm confirm) {
        return confirmRepository.save(confirm);
    }

    public Confirm findByUserAndCreateTimeBetween(User user, Date startDate, Date endDate) {
        return confirmRepository.findByUserAndCreateTimeBetween(user, startDate, endDate);
    }

    public List<Confirm> findByUser(User user) {
        return confirmRepository.findByUser(user);
    }
}
