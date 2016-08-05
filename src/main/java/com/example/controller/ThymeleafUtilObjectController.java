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
import org.springframework.validation.BindingResult;
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
public class ThymeleafUtilObjectController {
	@Autowired
	private MessageSource ms;
	
	/**
	 * params 通过参数名来区分不同的URL
	 * 例如：
	 * test?addRow=123
	 * test?removeRow=123
	 * 这两个参数是不同的，对应的controller的方法也是不同的
	 * @param name
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "thymeleafUtilObject")
	public String showInfo(HttpServletRequest request) {
		request.setAttribute("orderId", "12");
		return "thymeleafUtilObject";
	}
}
