package com.coderain.ecommercestore.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.coderain.ecommercestore.domain.User;

public class Customers implements Serializable {

	private List<User> customers;

	public Customers() {
	}

	public Customers(List<User> users) {
		this.customers = users;
	}

	public List<User> getUsers() {
		return customers;
	}

	public void setUsers(List<User> users) {
		this.customers = users;
	}

}
