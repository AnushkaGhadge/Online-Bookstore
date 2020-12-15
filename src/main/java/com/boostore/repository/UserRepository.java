package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	
	User findByEmail(String email);
}
