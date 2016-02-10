package com.sameer.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sameer.spring.dao.TestDAO;
import com.sameer.spring.model.TestDetails;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDAO testDAO;

	@Override
	@Transactional
	public TestDetails getTestById(int id) {
		return testDAO.getTestById(id);
	}
}
