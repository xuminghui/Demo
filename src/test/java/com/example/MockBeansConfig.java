package com.example;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.example.repository.BookRepository;

/**
 * 所有需要Mock的Bean，在这里定义。通过@Primary 在测试时候，注入响应的mock对象即可
 * @author xuminghui
 *
 */
@Configuration
@UsedForTesting
public class MockBeansConfig {
	@Bean
	@Primary
	public BookRepository createMockBookRepository() {
		return Mockito.mock(BookRepository.class);
	}
}