package com.example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
public class Book implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message="{book.isbn.null}")
	private String isbn;
	@NotEmpty(message = "{book.name.null}")  
    @Length(min = 5, max = 20, message = "{book.name.length.illegal}")  
    @Pattern(regexp = "[a-zA-Z]{5,20}", message = "{book.name.illegal}")  
	private String bookName;
	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	private Author author;
	
	@JsonIgnore//此属性表明忽略json转换object 和object转换json
	private String remark;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDateTime createTime;
	
	
	public Book(){
		
	}
	public Book(String isbn, String bookName, Author author,String remark) {
		this.isbn = isbn;
		this.bookName = bookName;
		this.author = author;
		this.remark = remark;
		this.createTime=LocalDateTime.now();
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
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
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
	
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	
	
}
