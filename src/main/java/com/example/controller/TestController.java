package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
@Controller
@RequestMapping("test")
public class TestController {
	@Autowired
	private  ResourceUrlProvider provider;
	@RequestMapping("/testAjax")
	@ResponseBody
	public  Ticket showData(){
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ticket ticket=new Ticket();
		ticket.setId("1");
		ticket.setName("name");
		return ticket;
	}
	@RequestMapping("/testAjax2")
	public  String testAngular(){ 
		return "testAngular";
	}
	@RequestMapping("testVersion")
	public  String testResourceVersion(){
		//返回一个经过md5加密的资源名称
		System.out.println(provider.getForLookupPath("/js123/home.js"));
		return "testStaticVersionResource";
	}
	public static class Ticket{
		private String id;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
