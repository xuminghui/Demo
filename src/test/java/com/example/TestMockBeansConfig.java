package com.example;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.repository.BookRepository;


@Configuration
@UsedForTesting
public class TestMockBeansConfig {
	@Bean
	@Primary
	public BookRepository createMockBookRepository() {
		return Mockito.mock(BookRepository.class);
	}
}