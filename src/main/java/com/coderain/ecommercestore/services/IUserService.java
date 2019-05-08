package com.coderain.ecommercestore.services;

import java.util.List;

import com.coderain.ecommercestore.domain.User;

public interface IUserService {

	User findOne(Long id);

	List<User> findAll();

	void save(User user);

}
