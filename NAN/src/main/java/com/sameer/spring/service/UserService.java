package com.sameer.spring.service;

import com.sameer.spring.model.User;

public interface UserService {

	public User getUserById(String username);
	public boolean validateUser(String username, String password);
}
