package com.example.formatter;

import org.springframework.core.convert.converter.Converter;

import com.example.enu.SEX;


public class String2SexConvertor implements Converter<String, SEX> {

    @Override
    public SEX convert(String enumValueStr) {
    	System.out.println("22222222222222"); 
        String value = enumValueStr.trim();
        if (value.isEmpty()) {
            return null;
        }
        return SEX.get(enumValueStr); 
    }
    
}