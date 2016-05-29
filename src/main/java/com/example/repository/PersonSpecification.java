package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.h2.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.example.entity.Person;

public class PersonSpecification {
	
	public static Specification<Person> queryByProperties(Person person){
		return new Specification<Person>() {
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<Predicate>();
				if(!ObjectUtils.isEmpty(person.getId())){
					list.add(cb.equal(root.get("id").as(Long.class), person.getId()));
				}
				if(!StringUtils.isNullOrEmpty(person.getUserName())){
					list.add(cb.equal(root.get("userName").as(String.class),person.getUserName()));
				}
				if(!StringUtils.isNullOrEmpty(person.getEmail())){
					list.add(cb.equal(root.get("email").as(String.class),person.getEmail()));
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
	};
}
