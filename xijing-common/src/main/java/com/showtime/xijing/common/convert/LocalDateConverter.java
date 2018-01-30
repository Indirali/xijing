package com.showtime.xijing.common.convert;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*******************************************************************************
 * Copyright (c) 2005-2016 LongDai, Inc.
 *
 * Contributors:
 * DonQuixote  on 19/11/2017 - 3:39 PM
 *
 *******************************************************************************/
public class LocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateConverter(String dateFormat) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public LocalDate convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, formatter);
    }
}
