package com.example.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 测试thymeleaf tag
 * @author xuminghui
 *
 */
@Controller
@RequestMapping(value="/test")
public class ThymeleafExampleController {
	@Autowired
	private MessageSource ms;
	@RequestMapping(value = "showName", method = RequestMethod.GET)
	public String showName() {
		String message=ms.getMessage("message", null, Locale.ENGLISH);
		System.out.println(message);
		return "thymeleafExample";
	}
	
	@RequestMapping(name="showuri")
	public String showURI(String userName) {
		return "testAny";
	}
}
