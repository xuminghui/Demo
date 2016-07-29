package com.example.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.User;
import com.example.enu.Color;
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
	public String showName(@RequestParam String name,HttpServletRequest request) {
		User user=new User(1l,"xiaodingdang");
		request.getSession().setAttribute("sessionAttribute", "this is sessionAttribute");
		request.getSession().setAttribute("user", user);
		request.getServletContext().setAttribute("applicationContext", "this is  applicationContext");
		String message=ms.getMessage("message", null, Locale.ENGLISH);
		return "thymeleafExample";
	}
	
	@RequestMapping(name="showuri")
	public String showURI(String userName) {
		return "testAny";
	}
	@ModelAttribute("colors")
	public List<Color> getColors(){
		return Arrays.asList(Color.GREEN);
	}
}
