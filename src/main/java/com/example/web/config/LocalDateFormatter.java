package com.example.web.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

public class LocalDateFormatter implements Formatter<LocalDate> {
	public static final String PATTERN = "YYYY/MM/DD";

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern(PATTERN));
	}


	@Override
	public String print(LocalDate object, Locale locale) {
		return DateTimeFormatter.ofPattern(PATTERN).format(object);
	}
}