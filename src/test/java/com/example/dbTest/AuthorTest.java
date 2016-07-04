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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.DemoApplication;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.repository.AuthorRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DemoApplication.class}) // 找到spring的配置
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorTest {
	public static final int ENTITY_COUNT = 100;
	@Autowired
	private AuthorRepository repository;

	@Before
	public void initData() {
		List<Author> authors=new ArrayList<Author>();
		for(int i=0;i<ENTITY_COUNT;i++){
			Author author=new Author();
			author.setName("author"+i);
			authors.add(author);
		}
		repository.save(authors);
	}
	@After
	public void deleteData(){
		repository.deleteAll();
	}
	@Test
	public void testAuthorCount() throws Exception {
		Assert.assertEquals(ENTITY_COUNT, repository.count());
	}
	@Test
	public void testAuthorAll(){
		List<Author> authors=repository.findAll();
		Assert.assertEquals(ENTITY_COUNT, authors.size());
	}
	@Test
	public void testAuthorById(){
		List<Author> authors=repository.findAll();
		Author temp=authors.get(0);
		Author author=repository.findOne(temp.getId());
		Assert.assertEquals(temp.getName(),author.getName());
	}
	/*@Test
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
	}*/

	
}
