package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Book;
import com.example.repository.BookRepository;

@RestController
@RequestMapping(value="/books")
public class BookController {
	@Autowired
	private BookRepository bookRepository;
	// 此处是在禁用了CSRF的功能后调试成功的，在开启情况下如何通过验证，有待于进一步研究
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Book create(@RequestBody Book book) {
		System.out.println("22222222222222");
		//bookRepository.save(book);
		return book;
	}

	/*
	 * @InitBinder //每一次请求，执行一次，因为propertyEditor是非线程安全的 后续版本使用formatter(线程安全的)
	 * public void initBinder(WebDataBinder binder) {
	 * binder.registerCustomEditor(Isbn.class, new IsbnEditor()); }
	 */

	@RequestMapping(value = "getbook/{isbn}", method = RequestMethod.GET)
	public Book getBook(@PathVariable("isbn") Book book) {
		return book;
	}

	@RequestMapping(value = "/books/session", method = RequestMethod.GET)
	public String getSessionId(HttpServletRequest request) {
		return request.getSession().getId();
	}

}
