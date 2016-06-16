package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class TestController {
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
