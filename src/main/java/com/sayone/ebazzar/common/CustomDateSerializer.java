package com.sayone.ebazzar.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CustomDateSerializer extends JsonSerializer<Long> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if(aLong == null){
            jsonGenerator.writeNull();
            return;
        }
        LocalDateTime date = Instant.ofEpochMilli(aLong).atZone(ZoneOffset.UTC).toLocalDateTime();
        date.format(dateTimeFormatter);
        jsonGenerator.writeString(date.format(dateTimeFormatter));
    }
}
