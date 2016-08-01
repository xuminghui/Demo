package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.example.entity.Apple;
import com.example.entity.Book;
import com.example.entity.User;
import com.example.enu.Color;
import com.example.enu.SEX;
import com.example.service.BookService;

/**
 * 测试thymeleaf tag
 * 
 * @author xuminghui
 *
 */
@Controller
@RequestMapping(value = "/test")
public class ThymeleafExampleController {
	@Autowired
	private MessageSource ms;
	@Autowired
	private BookService bookService;

	@RequestMapping(value = "showName", method = RequestMethod.GET)
	public String showName(@RequestParam String name, HttpServletRequest request) {
		User user = new User(1l, "xiaodingdang");
		request.getSession().setAttribute("sessionAttribute", "this is sessionAttribute");
		request.getSession().setAttribute("user", user);
		request.getServletContext().setAttribute("applicationContext", "this is  applicationContext");
		String message = ms.getMessage("message", null, Locale.ENGLISH);
		return "thymeleafExample";
	}

	@RequestMapping(name = "showuri")
	public String showURI(String userName) {
		return "testAny";
	}

	@ModelAttribute("colors")
	public List<String> getColors() {

		return Arrays.asList(Color.GREEN.getName(), Color.RED.getName(), Color.YELLOW.getName());
	}

	// 不是直接请求页面的URL，通过ModelAttribute来获得页面的数据
	// 注意：必须请求这个controller当中任何一个url,才会调用这个modelAttribute对应的方法
	@ModelAttribute("allBooks")
	public List<Book> populateAllBooks() {
		return bookService.findAllBooks();
	}

	//
	@ModelAttribute("allApples")
	public List<Apple> populateAllApples() {
		List<Apple> apples=new ArrayList<Apple>();
		for(int i=0;i<10;i++){
			Apple apple=new Apple();
			apple.setColor("red"+i);
			apple.setName("name"+i);
			apple.setSex(SEX.Female);
			apple.setCurrency(234927349724.22);
			apple.setCreateTime(new Date());
			apples.add(apple);
		}
		
		return apples;
	}
}
