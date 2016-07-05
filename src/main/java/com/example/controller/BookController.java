package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.example.service.BookService;

@RestController
@RequestMapping(value="/books")
public class BookController {
	@Autowired
	private BookService bookService;
	// 此处是在禁用了CSRF的功能后调试成功的，在开启情况下如何通过验证，有待于进一步研究
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Book create(@RequestBody Book book) {
		//bookRepository.save(book);
		return book;
	}


	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Book getBook(@PathVariable("id") long id) {
		return bookService.getBook(id);
	}

	@RequestMapping(value = "/books/session", method = RequestMethod.GET)
	public String getSessionId(HttpServletRequest request) {
		return request.getSession().getId();
	}
	@RequestMapping(value = "bookList", method = RequestMethod.GET)
	public List<Book> findBookList(@RequestParam MultiValueMap<String,String> params) {
		Page<Book> pageBooks=bookService.findBooks(Integer.valueOf(params.get("pageNum").get(0)), params.get("orderBy").get(0));
		System.out.println(pageBooks);
		return pageBooks.getContent();
	}
}
