package com.example.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

public class User implements Serializable {  
    public static interface OnlyIdView {}  
    public static interface OnlyNameView {}  
    public static interface AllView extends OnlyIdView, OnlyNameView {}  
  
    @JsonView(OnlyIdView.class)  
    private Long id;  
  
    @JsonView(OnlyNameView.class)  
    private String name; 
    public User(Long id,String name){
    	this.id=id;
    	this.name=name;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}  