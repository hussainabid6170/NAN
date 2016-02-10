package com.sameer.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sameer.spring.dao.UserDAO;
import com.sameer.spring.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public User getUserById(String userName) {
		return userDAO.getPersonById(userName);
	}

	@Override
	@Transactional
	public boolean validateUser(String userName, String password) {
		User u = getUserById(userName);
		if(u != null && password.equals(u.getPassword())){
			return true;
		}
		return false;
	}

	
}
