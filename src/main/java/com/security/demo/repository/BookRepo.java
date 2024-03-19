package com.security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.demo.dto.Books;

public interface BookRepo extends JpaRepository<Books, Integer>{
		
}
