package com.showtime.xijing.web.configs;

import com.showtime.xijing.common.convert.LocalDateConverter;
import com.showtime.xijing.common.convert.LocalDateTimeConverter;
import com.showtime.xijing.common.convert.SimpleDateConverter;
import com.showtime.xijing.common.exception.handler.BaseExceptionResolver;
import com.showtime.xijing.common.exception.handler.ValidateExceptionResolver;
import com.showtime.xijing.common.observerableEntity.ObservableEntityInterceptor;
import com.showtime.xijing.web.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
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
    private LoginInterceptor loginInterceptor;

    @Resource
    private BaseExceptionResolver baseExceptionResolver;

    @Resource
    private ValidateExceptionResolver validateExceptionResolver;

    @Resource
    private ObservableEntityInterceptor observableEntityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //该拦截器后的拦截器不能再有数据库操作
        registry.addInterceptor(observableEntityInterceptor);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(validateExceptionResolver);
        exceptionResolvers.add(baseExceptionResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SimpleDateConverter("yyyy-MM-dd"));
        registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
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
