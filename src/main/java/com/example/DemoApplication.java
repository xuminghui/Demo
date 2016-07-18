package com.example;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.springDemo.BaseProfile;
import com.example.springDemo.BaseService;

@SpringBootApplication
@EnableScheduling
@ComponentScan(excludeFilters=@ComponentScan.Filter(UsedForTesting.class)) 
public class DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	//MAP注入
	@Autowired
	private Map<String, BaseService> map;   
	//List注入
	@Lazy
	@Autowired
	private List<BaseService> list;
	@Autowired
	private BaseProfile profile;
	@PostConstruct
	public void logSomething() {
		logger.debug("Sample Debug Message");
		logger.debug("Sample Trace Message");
		
	System.out.println("mapSize: "+map.size());
	System.out.println("listSize: "+list.size());
	System.out.println(list.get(0));
	System.out.println(profile); 
	}

	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void run() {
		System.out.println("每十秒执行一次");
	}

	public static void main(String[] args) {
		//new SpringApplicationBuilder(DemoApplication.class).profiles("remote").run(args);
		SpringApplication.run(DemoApplication.class, args); 
	}
}
@interface	UsedForTesting	{} //定义一个专门为测试类标注的注解
