package com.example.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Apple implements Serializable{
	private String name;
	private String color;
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
	
}
