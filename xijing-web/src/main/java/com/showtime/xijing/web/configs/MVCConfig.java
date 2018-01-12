package com.showtime.xijing.web.configs;

import com.showtime.xijing.common.exception.handler.BaseExceptionResolver;
import com.showtime.xijing.common.exception.handler.ValidateExceptionResolver;
import com.showtime.xijing.web.interceptor.AuthInterceptor;
import com.showtime.xijing.web.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
@Slf4j
public class MVCConfig extends WebMvcConfigurerAdapter {

    @Resource
    private AuthInterceptor authInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Resource
    private BaseExceptionResolver baseExceptionResolver;

    @Resource
    private ValidateExceptionResolver validateExceptionResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //该拦截器后的拦截器不能再有数据库操作
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
        registry.addInterceptor(authInterceptor).addPathPatterns("/recruit/save").addPathPatterns("/report/filtrate").addPathPatterns("/report/like").addPathPatterns("/report/dislike");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(validateExceptionResolver);
        exceptionResolvers.add(baseExceptionResolver);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
        super.configureMessageConverters(converters);
    }

}
