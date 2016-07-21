package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Apple;

@Controller
@RequestMapping(value="/test")
public class ModelAttributeController {
	//注意使用@ModelAttribute和@RequestBody会导致apple的属性值没有了。。。为什么？
	@RequestMapping(value="/firstModelAttribute",method=RequestMethod.POST)
	public String firstRequest(@RequestBody Apple apple,HttpServletRequest request){
		System.out.println("apple: "+apple.getColor());
		request.setAttribute("apple", apple);
		return "showApple";
	}
	
	
	
	
}
