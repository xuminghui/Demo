package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.example.service.MailService;

public class InitRunner implements CommandLineRunner {
	@Autowired
	private MailService service;
	@Override
	public void run(String... args) throws Exception {
		System.out.println("初始化数据"+service);
	}

}
