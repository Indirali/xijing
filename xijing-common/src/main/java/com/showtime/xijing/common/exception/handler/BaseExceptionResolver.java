package com.showtime.xijing.common.exception.handler;

import com.showtime.xijing.common.Result;
import com.showtime.xijing.common.exception.BaseException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class BaseExceptionResolver implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof BaseException) {
            BaseException baseException=(BaseException) e;
            return returnMV(baseException.getCode(),baseException.getMessage());
        }
        if(e instanceof IllegalArgumentException){
            return returnMV(Result.code_fail.getValue(),e.getMessage());
        }
        return null;
    }
    private ModelAndView returnMV(Integer code,String msg){
        Map map = new HashMap();
        map.put("code", code);
        map.put("message", msg);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
