package com.showtime.xijing.common.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.expression.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

/*******************************************************************************
 * Copyright (c) 2005-2016 LongDai, Inc.
 *
 * Contributors:
 * DonQuixote  on 19/11/2017 - 3:34 PM
 * yyyy-MM-dd 转换成Date 对象
 *******************************************************************************/
@Slf4j
public class SimpleDateConverter implements Converter<String, Date> {


    private final SimpleDateFormat formatter;

    public SimpleDateConverter(String dateFormat) {
        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public Date convert(String source) {
        try {
            return formatter.parse(source);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }
}
