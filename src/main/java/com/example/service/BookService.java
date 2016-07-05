package com.example.service;

import java.util.List;

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
	@Autowired
	private BookRepository bookRepository;
	
	public Page<Book> findBooks(int pageNum,String orderBy){
		Pageable pageable=new PageRequest(pageNum, PageSize.PAGE_SIZE, Direction.DESC,"bookName");
		Page<Book> books=bookRepository.findAll(pageable);
		return books;
	}
}
