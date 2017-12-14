package com.showtime.xijing.service;

import com.showtime.xijing.entity.Confirm;
import com.showtime.xijing.repository.ConfirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        if (confirm.getId() != null) {
            confirm.setUpdateTime(new Date());
        } else {
            confirm.setCreateTime(new Date());
        }
        return confirmRepository.save(confirm);
    }

}