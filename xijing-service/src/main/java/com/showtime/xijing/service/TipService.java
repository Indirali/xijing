package com.showtime.xijing.service;

import com.showtime.xijing.entity.Tip;
import com.showtime.xijing.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/7
 * Time: 15:20
 **/
@Service
public class TipService {

    private TipRepository tipRepository;

    @Autowired
    public TipService(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    public Tip save(Tip tip) {
        return tipRepository.save(tip);
    }

}
