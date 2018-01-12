package com.showtime.xijing.convert;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.persistence.AttributeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2017/12/6
 * Time: 11:16
 **/
@Slf4j
public class EmojiConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String s) {
        try {
            return s == null ? null : URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.debug(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        try {
            return s == null ? null : URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.debug(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
}
