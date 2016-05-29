package com.example.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;
@Configuration
@PropertySource("classpath:config/Config.properties")
public class Config {
	public static int PAGE_SIZE;
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigure(){
		return new PropertySourcesPlaceholderConfigurer();
	}
	@Value("${page.size}")
	public void setPageSize(int pageSize){ 
		PAGE_SIZE=pageSize;
	}
	/*@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}*/
}
