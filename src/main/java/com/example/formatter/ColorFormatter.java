package com.example.formatter;


import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import com.example.enu.Color;
import com.example.enu.SEX;

public class ColorFormatter implements Formatter<Color> {
	@Autowired
	private MessageSource messageSource;

	@Override
	public String print(Color color, Locale local) {
		System.out.println("调用color2String"+color);
		return color.getValue();
	}

	@Override
	public Color parse(String color, Locale local) throws ParseException {
		System.out.println("调用stringToColor"+color);
		return Color.get(color);
	}


	
}
