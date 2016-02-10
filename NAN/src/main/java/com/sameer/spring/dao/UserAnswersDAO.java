package com.sameer.spring.dao;

import java.util.List;

import com.sameer.spring.model.UserAnswers;

public interface UserAnswersDAO {

	public void addUserAnswer(UserAnswers p);
	public void updateUserAnswer(UserAnswers p);
	public List<UserAnswers> listAnswersByUserName(String userName);
	public UserAnswers getAnswerByIdAndUserName(int questionId, String userName);
}
