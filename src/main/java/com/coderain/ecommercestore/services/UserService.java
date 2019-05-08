package com.coderain.ecommercestore.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderain.ecommercestore.domain.User;
import com.coderain.ecommercestore.repository.AuthorityRepository;
import com.coderain.ecommercestore.repository.UserRepository;

@Service
public class UserService implements IUserService {

	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository _userRepo;
	@Autowired
	private AuthorityRepository _authRepo;

	public List<User> findAll() {
		return this._userRepo.findAll();
	}

	public void save(User user) {
		this._userRepo.save(user);
	}

	public User create(User user) {
		User localUser = this._userRepo.findByUsername(user.getUsername()).get();

		if (localUser != null) {
			logger.info("User with username {} already exist. Nothing will be done.", user.getUsername());
		} else {
			localUser = _userRepo.save(localUser);
		}
		return localUser;
	}

	@Override
	public User findOne(Long id) {
		return this._userRepo.findById(id).get();
	}

}
