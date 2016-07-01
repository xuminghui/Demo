package com.example.dbTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.DemoApplication;
import com.example.entity.Book;
import com.example.repository.BookRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DemoApplication.class}) // 找到spring的配置
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookTest {
	public static final int ENTITY_COUNT = 100;
	@Autowired
	private BookRepository repository;

	@Before
	public void initData() {
		List<Book> books=new ArrayList<Book>();
		for(int i=0;i<100;i++){
			Book book=new Book("isbn"+i,"bookName"+i,"author"+i,"remark"+i);
			books.add(book);
		}
		repository.save(books);
	}
	@After
	public void deleteData(){
		repository.deleteAll();
	}
	@Test
	public void testBookCount() throws Exception {
		Assert.assertEquals(ENTITY_COUNT, repository.count());
	}
	@Test
	public void testGetBookByIsbn(){
		Book book=repository.findByIsbn("isbn99");
		Assert.assertEquals("author99", book.getAuthor());
	}
	@Test
	public void testUpdateBook(){
		Book book=repository.findByIsbn("isbn1");
		book.setAuthor("author12");
		repository.save(book);
		book=repository.findByIsbn("isbn1");
		Assert.assertEquals("author12", book.getAuthor());
	}

	
}
