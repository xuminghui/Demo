package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Person;
public interface PersonRepository extends JpaRepository<Person, String>,JpaSpecificationExecutor<Person>{
	/**
	 * 根据userName查询列表
	 * @param userName
	 * @return
	 */
	
	Person findByUserName(String userName);
	
	/**
	 * 根据userName and email查询用户
	 * @param userName
	 * @param email
	 * @return
	 */
	Person findByUserNameAndEmail(String userName,String email);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Person findById(Long id);
	
	/**
	 * 采用@Query查询
	 * @param name
	 * @param email
	 * @return
	 */
	@Query("select p from Person p where p.userName=:name and p.email=:email")
	Person withUserNameQuery(@Param("name") String name,@Param("email") String email);
}
