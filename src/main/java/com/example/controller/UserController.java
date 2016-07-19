package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Person;
import com.example.entity.User;
import com.example.entity.User.OnlyIdView;
import com.example.repository.PersonRepository;
import com.example.web.config.MyConfig;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
public class UserController {

	@Autowired
	PersonRepository personDao;
	@Autowired
	MyConfig config;
	// handle when logged user go to login page
	@RequestMapping("/login")
	public String login() {
		config.output();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) {
			return "login";
		} else {
			return "home"; 
		}
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(Person newUser) {
		if(1==1)
		throw new RuntimeException("1111");
		try {
			if (personDao.findByUserName(newUser.getUserName())!=null) {
				return "redirect:" + "/login?registration&error";
			} else {
				newUser.setRole("ROLE_TEST");
				personDao.save(newUser);
				
				return "redirect:" + "/login?registration&success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/login?registration&errorServer";
		}

	}
	/**
	 * 指定json返回的实体数据
	 * @return
	 */
	@RequestMapping("/test/testJsonView")
	@JsonView(User.OnlyNameView.class)
	//@JsonView(OnlyIdView.class)
	@ResponseBody
	public User testJsonView() {
		return new User(1l,"xuminghui");
	}
}
