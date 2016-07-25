package com.example.controller;

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
	@RequestMapping(value = "showName", method = RequestMethod.GET)
	public String showName() {
		return "thymeleafExample";
	}
	
	@RequestMapping(name="showuri")
	public String showURI(String userName) {
		return "testAny";
	}
}
