package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping; 

@Controller
public class IndexController {
	@RequestMapping("/")
	public String getByUserName(){
		System.out.println("11111");
		return "index";
	}
	
}
