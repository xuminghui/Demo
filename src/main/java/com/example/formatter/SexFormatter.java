package com.example.formatter;


import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import com.example.enu.SEX;

public class SexFormatter implements Formatter<SEX> {
	@Autowired
	private MessageSource messageSource;

	@Override
	public String print(SEX sex, Locale local) {
		if(sex==null){
			sex=SEX.Unknown;
		}
		System.out.println("调用sex2String"+sex);
		return sex.getDescription();
	}

	@Override
	public SEX parse(String sex, Locale local) throws ParseException {
		System.out.println("调用stringToSex"+sex);
		return SEX.get(sex.trim());
	}


	
}
