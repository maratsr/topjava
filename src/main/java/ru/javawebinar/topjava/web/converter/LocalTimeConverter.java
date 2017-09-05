package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.RU_TIME_FORMATTER;

public class LocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String s) {
        try {
            return LocalTime.parse(s, RU_TIME_FORMATTER);
        } catch (Exception ex) {
            return null;
        }
    }
}
