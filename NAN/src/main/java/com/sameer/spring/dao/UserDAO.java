package com.sameer.spring.dao;

import com.sameer.spring.model.User;

public interface UserDAO {
	public User getPersonById(String userName);
}
