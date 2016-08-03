package com.example.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.example.enu.Color;
import com.example.enu.SEX;

public class Apple implements Serializable{
	private String name;
	private String color;
	//通过thymeleaf的双括号${{apple.createTime}}，再根据这个注解即可完成.不需要在config中指定formatter(应该是spring boot自动已经注入了)
	//
	//或者通过thymeleaf的#dates的工具类执行也可以
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	private List<Row> rows=new ArrayList<Row>();
	
	private SEX  sex;
	//此处不需要初始化  ，页面传递过来的数值会覆盖默认的值的。也会报空指针；所以要在controller判断一下是否为null
	private List<Color> colors; 
	
	private boolean hasReached;
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
	public boolean isHasReached() {
		return hasReached;
	}
	public void setHasReached(boolean hasReached) {
		this.hasReached = hasReached;
	}
	public List<Color> getColors() {
		return colors;
	}
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	public List<Row> getRows() {
		return rows;
	}
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	
	
	
	
	
}
