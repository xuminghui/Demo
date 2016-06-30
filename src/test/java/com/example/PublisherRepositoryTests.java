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

import com.example.repository.BookRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, TestMockBeansConfig.class })
@IntegrationTest
public class PublisherRepositoryTests {
	@Autowired
	private BookRepository repository;

	@Before
	public void setupPublisherRepositoryMock() {
		Mockito.when(repository.count()).thenReturn(1L);
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