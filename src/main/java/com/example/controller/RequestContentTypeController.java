package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@RequestMapping("/testContentType")
public class RequestContentTypeController {
	@RequestMapping(value = "ContentType", method = RequestMethod.GET,headers = "Content-Type=text/html;charset=UTF-8") 
	public  String showData(HttpServletRequest request){
		System.out.println("contentType: "+request.getContentType());
		return "testAny";
	}
}
