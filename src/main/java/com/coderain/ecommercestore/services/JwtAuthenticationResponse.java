package com.coderain.ecommercestore.services;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

	/**
	 * @author zhora
	 * @version 0.1
	 */
	private static final long serialVersionUID = 1L;
	
	private final String token;
	
	public JwtAuthenticationResponse(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
}
