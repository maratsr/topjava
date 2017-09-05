package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

import static ru.javawebinar.topjava.util.DateTimeUtil.RU_DATE_FORMATTER;

public final class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        try {
            return LocalDate.parse(s, RU_DATE_FORMATTER);
        } catch (Exception ex) {
            return null;
        }
    }
}
