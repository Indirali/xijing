package com.showtime.xijing.admin.configs;

import com.showtime.xijing.common.observerableEntity.ObservableEntityListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ObservableEntityListener observableEntityListener() {
        return new ObservableEntityListener();
    }

}

