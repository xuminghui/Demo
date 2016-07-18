package com.example.springDemo;

import javax.annotation.Priority;

import org.springframework.stereotype.Service;

import com.example.entity.Book;
//@Order
@Priority(value = 1)
@Service
public class ServiceB extends BaseService<Book> {

}
