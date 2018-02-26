package com.zeroexception.oauth2implementation;

import com.zeroexception.oauth2implementation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Oauth2ImplementationApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ImplementationApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
//		this.userService.addUser(new User()
//				.setFirstName("John")
//				.setLastName("Carpenter")
//				.setEmail("john@gmail.com"));
	}
}
