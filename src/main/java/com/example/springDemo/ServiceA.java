package com.example.springDemo;

import javax.annotation.Priority;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.example.entity.Book;
//@Order
@Priority(value = 2)
@Service
//@Lazy //初始化延迟
public class ServiceA extends BaseService<Book> { 
	public ServiceA(){
		System.out.println("初始化A");
	}
}
