package com.example.web.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

public class LocalDateFormatter implements Formatter<LocalDateTime> {
	public static final String PATTERN = "YYYY/MM/DD";

	@Override
	public LocalDateTime parse(String text, Locale locale) throws ParseException {
		return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(PATTERN));
	}


	@Override
	public String print(LocalDateTime object, Locale locale) {
		return DateTimeFormatter.ofPattern(PATTERN).format(object);
	}
}