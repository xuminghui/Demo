package com.example;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@ComponentScan(excludeFilters=@ComponentScan.Filter(UsedForTesting.class)) 
public class DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@PostConstruct
	public void logSomething() {
		logger.debug("Sample Debug Message");
		logger.debug("Sample Trace Message");
	}

	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void run() {
		System.out.println("每十秒执行一次");
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
@interface	UsedForTesting	{} //定义一个专门为测试类标注的注解
