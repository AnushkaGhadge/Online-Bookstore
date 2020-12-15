package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(String name);
}
