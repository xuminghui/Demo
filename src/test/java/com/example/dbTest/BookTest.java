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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.DemoApplication;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.example.springDemo.BaseProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DemoApplication.class}) // 找到spring的配置
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local") //测试使用的Profile
public class BookTest {
	public static final int ENTITY_COUNT = 100;
	@Autowired
	private BookRepository repository;
	@Autowired
	private BaseProfile profile;
	@Before
	public void initData() {
		System.out.println(profile);
		List<Book> books=new ArrayList<Book>();
		for(int i=0;i<ENTITY_COUNT;i++){
			Author author=new Author();
			author.setName("author"+i);
			Book book=new Book("isbn"+i,"bookName"+i,author,"remark"+i);
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
		Assert.assertEquals("author99", book.getAuthor().getName());
	}
	@Test
	public void testUpdateBook(){
		Book book=repository.findByIsbn("isbn1");
		Author author=new Author();
		author.setName("author12");
		book.setAuthor(author);
		repository.save(book);
		book=repository.findByIsbn("isbn1");
		Assert.assertEquals("author12", book.getAuthor().getName());
	}
	
	@Test
	public void testPageAndSort(){
		Pageable pageable=new PageRequest(0,20,Direction.DESC,"isbn");
		Page<Book> page=repository.findAll(pageable);
		List<Book> books=page.getContent();
		Assert.assertEquals(20, books.size());
		Assert.assertEquals("isbn99", books.get(0).getIsbn());
	}

	
}
