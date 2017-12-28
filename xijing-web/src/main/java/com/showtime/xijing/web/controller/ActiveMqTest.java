package com.showtime.xijing.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/26
 * Time: 16:23
 **/
@Slf4j
@RestController
@EnableScheduling
@RequestMapping(value = "/activemq")
public class ActiveMqTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Scheduled(fixedRate = 1000000)
    // 每3s执行1次
    public void send() {
        log.info(">> send !!!");
        this.sendMessage("发送成功,哈哈哈哈！！！");
    }

    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage(String message) {
        jmsMessagingTemplate.convertAndSend("ActiveMQQueue", message);
    }

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = "ActiveMQQueue")
    public void receiveQueue(String text) {
        System.out.println("收到的报文为:" + text);
    }

}
