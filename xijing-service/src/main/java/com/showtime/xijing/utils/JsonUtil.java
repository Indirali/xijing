package com.showtime.xijing.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    private static ObjectWriter objectWriter = new ObjectMapper().writer();

    private JsonUtil() {
    }

    public static String toJsonString(Object obj) {
        try {
            return objectWriter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
