package com.showtime.xijing.common.exception.handler;

import com.showtime.xijing.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ValidateExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errs = bindException.getAllErrors();
            String msg;
            Map map = new HashMap();
            if (CollectionUtils.isEmpty(errs)) {
                msg = "系统错误";
            } else {
                ObjectError error = errs.get(0);
                msg = error.getDefaultMessage();
                map.put("filed", ((FieldError) error).getField());
            }
            map.put("code", Result.code_fail.getValue());
            map.put("message", msg);
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
        return null;
    }
}
