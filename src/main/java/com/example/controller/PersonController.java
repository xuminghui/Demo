package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Person;
import com.example.repository.PersonRepository;
import com.example.repository.PersonSpecification;
import com.example.service.PersonService;
import com.example.web.config.Config;
import com.example.web.view.UserSession; 

@RestController
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private PersonRepository personRepository;
	
	
	@RequestMapping(method=RequestMethod.GET,path="persons/{userName}")
	public Person getByUserName(@PathVariable String userName){
		System.out.println(personRepository);
		return personService.findOnePerson(userName);
	}
	@RequestMapping(method=RequestMethod.POST,path="save")
	public Person save(@Valid Person person){
		return personRepository.save(person);
	}
	
	
	@RequestMapping("/queryPerson")
	public Person getPersonByUserNameAndEmail(String userName,String email){
		return personRepository.findByUserNameAndEmail(userName, email);
	}
	/*@RequestMapping("/{id}")
	public Person queryById(@PathVariable Long id){
		return personRepository.findById(id);
	}*/
	
	@RequestMapping("/page")
	public Page<Person> page(Person person){
		Page<Person> pagePerson=personRepository.findAll(PersonSpecification.queryByProperties(person),new PageRequest(0,Config.PAGE_SIZE,Sort.Direction.DESC,"userName")); 
		return pagePerson;
	}
}
