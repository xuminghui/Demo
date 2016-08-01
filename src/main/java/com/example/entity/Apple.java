package com.example.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.example.enu.SEX;
import com.thoughtworks.xstream.converters.extended.CurrencyConverter;

public class Apple implements Serializable{
	private String name;
	private String color;
	//通过thymeleaf的双括号${{apple.createTime}}，再根据这个注解即可完成.不需要在config中指定formatter(应该是spring boot自动已经注入了)
	//
	//或者通过thymeleaf的#dates的工具类执行也可以
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date createTime;
	
	private SEX  sex;
	@NumberFormat(style=Style.CURRENCY/*,pattern="#,###.##"*/)
	private double currency;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public SEX getSex() {
		return sex;
	}
	public void setSex(SEX sex) {
		this.sex = sex;
	}
	public double getCurrency() {
		return currency;
	}
	public void setCurrency(double currency) {
		this.currency = currency;
	}
	
	
	
	
}
