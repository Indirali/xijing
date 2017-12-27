package com.showtime.xijing.service;

import com.showtime.xijing.enums.PushType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/26
 * Time: 17:23
 **/
@Slf4j
@Service
public class ActiveMQService {

    public final ExecutorService pushExecutor = Executors.newFixedThreadPool(10);

    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public ActiveMQService(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    public void userPush(PushType pushType) {
        pushExecutor.execute(() -> jmsMessagingTemplate.convertAndSend("userQueue", pushType));
    }

}
