package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.example.service.MailService;

public class BookInitRunner implements CommandLineRunner {
	@Autowired
	private MailService service;
	@Autowired
	private BookRepository bookRepository;	
	@Override
	public void run(String... args) throws Exception {
		/*List<Book> list=new ArrayList<Book>();
		for(int i=0;i<100;i++){
			Book book=new Book("978-3-16-148410-"+i,"bookName"+i,"author"+i);
			list.add(book);
		}
		bookRepository.save(list);*/
	}

}
