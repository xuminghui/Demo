package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Person;
import com.example.repository.PersonRepository;
import com.example.repository.UserDetailsDao.ROLE;

@Controller
public class UserController {

	@Autowired
	PersonRepository personDao;

	// handle when logged user go to login page
	@RequestMapping("/login")
	
	public String login() {
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
}
