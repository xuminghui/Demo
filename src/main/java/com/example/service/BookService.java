package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.constant.PageSize;
import com.example.entity.Book;
import com.example.repository.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepository;
	//基于CGLIB的类代理不再要求类必须有空参构造器了：基于构造器的注入。好处：构造完成，对象就不可以修改了
	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Page<Book> findBooks(int pageNum, String orderBy) {
		Pageable pageable = new PageRequest(pageNum, PageSize.PAGE_SIZE, Direction.DESC, "bookName");
		Page<Book> books = bookRepository.findAll(pageable);
		return books;
	}

	public Book getBook(long id) {
		return bookRepository.findOne(id);
	}
}
