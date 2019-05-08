package com.coderain.ecommercestore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.coderain.ecommercestore.domain.User;
import com.coderain.ecommercestore.repository.AuthorityRepository;
import com.coderain.ecommercestore.repository.UserRepository;
import com.coderain.ecommercestore.security.jwt.SecurityUtility;
import com.coderain.ecommercestore.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ECommerceStoreApplicationTests {

	@Configuration
	static class UserServiceTestContextConfiguration {
		@Bean
		public UserService userService() {
			return new UserService();
		}

	}

	@Autowired
	private UserService userService;


	@MockBean
	private UserRepository userRepository;
	@MockBean
	private AuthorityRepository roleRepository;

	public Set<User> users = new HashSet<User>();

	@Before
	public void initUsers() {

		
		User user = new User();
		user.setUsername("zhora");
		user.setPassword(SecurityUtility.passwordEncoder().encode("zhora007"));
		user.setEnabled(true);


		userService.save(user);
		users.add(user);

		User userAdmin = new User();
		userAdmin.setUsername("admin");
		userAdmin.setPassword(SecurityUtility.passwordEncoder().encode("admin007"));
		userAdmin.setEnabled(true);
		userService.save(userAdmin);
		users.add(userAdmin);
		// users = this.userService.findAll();

	}

	@Test
	public void testUserList() {
		assertThat(users).isNotNull();
	}

	@Test
	public void testUserListSize() {
		boolean listBiggerThan0 = users.size() > 0;
		int arrSize = users.size();
		assertTrue(listBiggerThan0);

		// assertArrayEquals(2, arrSize);
	}

}
