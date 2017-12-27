package com.showtime.xijing.web.configs;

import com.showtime.xijing.common.observerableEntity.ObservableEntityListener;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class BeanConfig {

    @Bean
    public ObservableEntityListener observableEntityListener() {
        return new ObservableEntityListener();
    }

    @Bean
    public Queue userQueue() {
        return new ActiveMQQueue("userQueue");
    }

    @Bean
    public Queue recruitQueue() {
        return new ActiveMQQueue("recruitQueue");
    }

}

