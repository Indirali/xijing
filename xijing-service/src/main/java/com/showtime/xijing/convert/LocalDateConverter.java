package com.showtime.xijing.convert;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

/*******************************************************************************
 * Copyright (c) 2005-2016 LongDai, Inc.
 *
 * Contributors:
 * DonQuixote  on 19/11/2017 - 3:41 PM
 *
 *******************************************************************************/
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate entityData) {
        return entityData == null ? null : Date.valueOf(entityData);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData == null ? null : dbData.toLocalDate();
    }
}
