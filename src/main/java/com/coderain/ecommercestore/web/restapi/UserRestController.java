package com.coderain.ecommercestore.web.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderain.ecommercestore.domain.User;
import com.coderain.ecommercestore.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	@Autowired
	private UserService _userService;

	@GetMapping("/get-all-users")
	public List<User> getAllUsers() {
		return this._userService.findAll();
	}

}
