package com.example.web.config;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
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
	
	
	@Value("classpath:config/Config.properties")
	private Resource testFile;
	
	@Value("http://www.baidu.com")
	private Resource testURL;
	
	public void output(){
		try {
			System.out.println(IOUtils.toString(testFile.getInputStream()));
			System.out.println(IOUtils.toString(testURL.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
