package com.example.controller;

import java.util.Arrays;
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

import com.example.entity.Apple;
import com.example.entity.Row;
import com.example.entity.User;
import com.example.enu.Color;

/**
 * 测试thymeleaf tag
 * 
 * @author xuminghui
 *
 */
@Controller
@RequestMapping(value = "/test")
public class DynamicFieldController {
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
	@RequestMapping(value = "dynamic", params={"addRow"})
	public String addRow(final Apple apple, final BindingResult bindingResult,HttpServletRequest request) {
		apple.getRows().add(new Row());
		printApple(apple);
		return "dynamicField";
	}
	@RequestMapping(value = "dynamic", params={"removeRow"})
	public String removeRow(final Apple apple, final BindingResult bindingResult,HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));    apple.getRows().remove(rowId.intValue()); 
		printApple(apple);
		return "dynamicField";
	}
	private void printApple(Apple apple){
		List<Row> rows=apple.getRows();
		for(Row row:rows){
			System.out.println(row.getName());
		}
	}
	@RequestMapping(value = "dynamic", method = RequestMethod.GET)
	public String show() {
		return "dynamicField";
	}
	@ModelAttribute("apple")
	public Apple getApple(){
		Apple apple=new Apple();
		return apple;
	}
	@ModelAttribute("colors")
	public List<Color> getColors() {
		return Arrays.asList(Color.GREEN, Color.RED, Color.YELLOW);
	}
}
