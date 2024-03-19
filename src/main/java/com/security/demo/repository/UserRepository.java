package com.security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.demo.dto.User;

import java.util.List;



public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByEmail(String email);



	



	boolean existsByMobile(long mobile);







	User findByEmail(String email);	
	

}
