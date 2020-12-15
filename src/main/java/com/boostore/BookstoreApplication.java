package com.boostore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.boostore.domain.User;
import com.boostore.domain.security.Role;
import com.boostore.domain.security.UserRole;
import com.boostore.service.UserService;
import com.boostore.utility.SecurityUtility;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("Niraj");
		user1.setLastName("Lonkar");
		user1.setUsername("nir");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("nush"));
		user1.setEmail("nirajlonkar075@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role = new Role();
		
		role.setRoleId(1);
		role.setName("ROLE_USER");
		
		userRoles.add(new UserRole(user1, role));
		userService.createUser(user1, userRoles);

	}

}
