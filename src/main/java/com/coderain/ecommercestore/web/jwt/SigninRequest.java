package com.coderain.ecommercestore.web.jwt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SigninRequest {

	@NotBlank
	@Size(min = 3, max = 50)
	private String usename;

	@NotBlank
	@Size(min = 6, max = 50)
	private String password;

	public String getUsename() {
		return usename;
	}

	public void setUsename(String usename) {
		this.usename = usename;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
