package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Book {
	@Id
	@GeneratedValue
	private Long id;
	private String isbn;
	@NotNull
	//@JsonIgnore  //此属性表明忽略json转换object 和object转换json
	private String bookName;
	@NotNull
	private String author;
	
	@JsonIgnore
	private String remark;
	public Book(){
		
	}
	public Book(String isbn, String bookName, String author,String remark) {
		this.isbn = isbn;
		this.bookName = bookName;
		this.author = author;
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
