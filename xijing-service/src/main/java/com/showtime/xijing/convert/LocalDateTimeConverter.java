package com.showtime.xijing.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/*******************************************************************************
 * Copyright (c) 2005-2016 LongDai, Inc.
 *
 * Contributors:
 * DonQuixote  on 19/11/2017 - 1:23 PM
 *
 *******************************************************************************/
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        return entityValue == null ? null : Timestamp.valueOf(entityValue);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbValue) {
        return dbValue == null ? null : dbValue.toLocalDateTime();
    }
}