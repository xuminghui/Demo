package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Person {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String userName;
	@NotNull
	private String password;
	@NotNull
	@Email
	private String email;
	@Temporal(TemporalType.DATE)
	private Date birth;
	
	@CreationTimestamp   
	@Column(updatable=false)
	private Date createdTime;//每次插入时，都会执行；更新时，不执行
	
	
	public Person() {
	}

	public Person(String userName, String password, String email) {
		this.userName = userName;
		this.password = password;
		this.email = email; 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		System.out.println("sadfsadf:" +birth);
		this.birth = birth;
	}

	
	

}
