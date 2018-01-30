package com.showtime.xijing.common.convert;

/*******************************************************************************
 * Copyright (c) 2005-2016 LongDai, Inc.
 *
 * Contributors:
 * DonQuixote  on 19/11/2017 - 12:57 PM
 * yyyy-MM-dd 转换成 LocalDateTime 对象
 *******************************************************************************/

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter;

    public LocalDateTimeConverter(String dateFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        if (source.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return LocalDate.parse(source).atStartOfDay();
        } else {
            return LocalDateTime.parse(source, formatter);
        }
    }
}
