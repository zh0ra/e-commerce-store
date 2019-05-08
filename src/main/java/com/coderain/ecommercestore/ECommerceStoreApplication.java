package com.coderain.ecommercestore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coderain.ecommercestore.domain.User;
import com.coderain.ecommercestore.security.jwt.SecurityUtility;
import com.coderain.ecommercestore.services.UserService;

@SpringBootApplication
public class ECommerceStoreApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ECommerceStoreApplication.class);

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ECommerceStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user = new User();
		user.setUsername("zhora");
		user.setPassword(SecurityUtility.passwordEncoder().encode("zhora007"));
		user.setEnabled(true);

		userService.save(user);

		User userAdmin = new User();
		userAdmin.setUsername("admin");
		userAdmin.setPassword(SecurityUtility.passwordEncoder().encode("admin007"));
		userAdmin.setEnabled(true);
		userService.save(userAdmin);
	}
}
