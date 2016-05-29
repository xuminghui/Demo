package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Person;
import com.example.web.view.UserSession; 

@Controller
public class IndexController {
	@Autowired
	private UserSession userSession;
	@RequestMapping("/")
	public String getByUserName(Person person){
		return "index";
	}
	@ModelAttribute
	public Person populate(){
		return userSession.toForm();
	}
	
}
