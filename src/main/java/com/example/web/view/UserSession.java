package com.example.web.view;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.entity.Person;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {
	private String userName;
	private String password;
	private String email;
	private Date birth=new Date();
	public Person toForm() {
		Person person = new Person();
		person.setUserName(userName);
		person.setPassword(password);
		person.setEmail(email);
		person.setBirth(new Date());
		return person;
	}
}
