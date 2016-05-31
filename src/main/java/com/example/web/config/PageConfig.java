package com.example.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("classpath:config/Config.properties")
//对于在application.properties里面的属性，可以直接使用@Value 
public class PageConfig {
	public static int PAGE_SIZE;
	@Value("${page.size}")
	public void setPageSize(int pageSize){ 
		PAGE_SIZE=pageSize; 
	}
}
