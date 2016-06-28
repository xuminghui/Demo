package com.example.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Book;

@RestController
public class BookController {
	//此处是在禁用了CSRF的功能后调试成功的，在开启情况下如何通过验证，有待于进一步研究
	@RequestMapping(value = "/books", method = RequestMethod.POST)
	public  Book create(@RequestBody Book book) {
		System.out.println(book);
		return book; 
	}
}
