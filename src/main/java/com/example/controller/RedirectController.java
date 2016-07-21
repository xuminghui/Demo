package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Apple;

@Controller
@RequestMapping(value="/test")
public class RedirectController {
	@RequestMapping(value="/firstPost",method=RequestMethod.POST)
	public String firstRequest(@ModelAttribute("apple") @RequestBody Apple apple,
			final RedirectAttributes redirectAttributes){
		System.out.println("apple: "+apple);
		redirectAttributes.addFlashAttribute("message", "Successfully added..");
		redirectAttributes.addFlashAttribute("apple", apple);
		return "redirect:shwoApple";
	}
	
	@RequestMapping(value="/showApple",method=RequestMethod.GET)
	public String redirect(@ModelAttribute("apple") Apple apple){
		System.out.println(apple.getColor());
		return "showApple";
	}
	
	
	
}
