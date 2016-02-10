package com.sameer.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sameer.spring.dao.UserAnswersDAO;
import com.sameer.spring.model.UserAnswers;

@Service
public class UserAnswersServiceImpl implements UserAnswersService {
	
	@Autowired
	private UserAnswersDAO	userAnswersDAO;

	@Override
	@Transactional
	public List<UserAnswers> getUserAnswers(String userName) {
		return userAnswersDAO.listAnswersByUserName(userName);
	}

	@Override
	@Transactional
	public UserAnswers getUserAnswerByQuestionByIdAnduserName(int questionId,
			String userName) {
		return userAnswersDAO.getAnswerByIdAndUserName(questionId, userName);
	}

	@Override
	@Transactional
	public void addUserAnswer(UserAnswers p) {
		userAnswersDAO.addUserAnswer(p);
	}

	@Override
	@Transactional
	public void updateUserAnswer(UserAnswers p) {
		userAnswersDAO.updateUserAnswer(p);
	}
}
