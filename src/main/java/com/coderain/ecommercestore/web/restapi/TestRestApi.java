package com.coderain.ecommercestore.web.restapi;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestApi {

	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccount() {
		return "User content";
	}

	@GetMapping("/api/test/guest")
	@PreAuthorize("hasRole('GUEST') or hasRole('GUEST')")
	public String gurdyAccount() {
		return "Guest content";
	}

	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccount() {
		return "Admin content";
	}

}
