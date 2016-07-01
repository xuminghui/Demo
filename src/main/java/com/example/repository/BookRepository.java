package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.entity.Book;

//@RepositoryRestResource(collectionResourceRel = "writers", path = "writers")
public interface BookRepository extends JpaRepository<Book, String> { 
	Book findByIsbn(String isbn);
}
