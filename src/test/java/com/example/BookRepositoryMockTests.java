package com.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.entity.Book;
import com.example.repository.BookRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, MockBeansConfig.class })
@IntegrationTest
public class BookRepositoryMockTests {
	@Autowired
	private BookRepository repository;

	@Before
	public void setupPublisherRepositoryMock() {
		Mockito.when(repository.count()).thenReturn(1L);
		Book book = new Book("isbnMock","bookNameMock","authorMock","remarkMock");
		Mockito.when(repository.findByIsbn(Mockito.anyString())).thenReturn(book);
	}
	@Test
	public void getBookTest(){
		Book book=repository.findByIsbn(Mockito.anyString());
		Assert.assertEquals("isbnMock", book.getIsbn());
	}
	@Test
	public void publishersExist() {
		Assert.assertEquals(1, repository.count());
	}

	@After
	public void resetPublisherRepositoryMock() {
		Mockito.reset(repository);
	}
}