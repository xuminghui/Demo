package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.example.entity.Person;
import com.example.repository.PersonRepository;
import com.example.repository.PersonSpecification;

@Service
public class PersonService {
	@Autowired
	private PersonRepository personRepository;
	
	public Person findOnePerson(String userName){
		Person person=new Person();
		person.setUserName(userName);
		return personRepository.findOne(PersonSpecification.queryByProperties(person));
	}
	@Secured("ROLE_USER")
	public Person save(Person person){
		return personRepository.save(person);
	}
}
