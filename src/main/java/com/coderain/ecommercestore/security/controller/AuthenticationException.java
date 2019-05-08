package com.coderain.ecommercestore.security.controller;

public class AuthenticationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -62873628531748L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
