package com.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.entity.Person;
import com.example.repository.PersonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonTests {
	@Autowired
	private PersonRepository personRepository;
	@Test
	public void savePersons(){
		System.out.println("setup");
		List<Person> list=new ArrayList<Person>();
		for(int i=0;i<100;i++){
			list.add(new Person("userName"+i,"password"+i,"xuminghui"+i+"@uuzz.com"));
		}
		personRepository.save(list);
	}
	@Test
	public  void test1deleteAll(){
		personRepository.deleteAll();
	}

	@Test
	public void test3PagingPerson(){
		Page<Person> pagePeople=personRepository.findAll(null,new PageRequest(1,2));
		List<Person> list=pagePeople.getContent();
		System.out.println(list.get(0).getId());
	}

}
