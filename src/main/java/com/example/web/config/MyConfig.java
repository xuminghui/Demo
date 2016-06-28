package com.example.web.config;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
@Configuration
@ConfigurationProperties(prefix="author",locations={"classpath:config/Config.properties"})
public class MyConfig {
	public MyConfig(){
		System.out.println("12344555");
	}
	private String name;
	private Long age;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}


	@Value("classpath:config/Config.properties")
	private Resource testFile;
	
	@Value("http://www.baidu.com")
	private Resource testURL;
	
	public void output(){
		try {
			System.out.println(IOUtils.toString(testFile.getInputStream()));
			System.out.println(IOUtils.toString(testURL.getInputStream()));
			System.out.println("age:" +age);
			System.out.println("name: "+name);
			System.out.println("pageSize: "+PageConfig.PAGE_SIZE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
