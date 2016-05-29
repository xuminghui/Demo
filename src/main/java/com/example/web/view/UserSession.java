package com.example.web.view;

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
	public Person toForm() {
		Person person = new Person();
		person.setUserName(userName);
		person.setPassword(password);
		person.setEmail(email);
		return person;
	}
}
