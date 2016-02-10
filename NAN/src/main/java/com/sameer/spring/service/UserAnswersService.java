package com.sameer.spring.service;

import java.util.List;

import com.sameer.spring.model.UserAnswers;

public interface UserAnswersService {
	/**
	 * this will return all answers subitted by user
	 * @param userName
	 * @return
	 */
	public List<UserAnswers> getUserAnswers(String userName);
	
	/**
	 * this method will return the answer model if user has submitted the answer, else will return null
	 * @param questionId
	 * @param userName
	 * @return
	 */
	public UserAnswers getUserAnswerByQuestionByIdAnduserName(int questionId, String userName);
	/**
	 * to add an answer for a question by user
	 * @param p
	 */
	public void addUserAnswer(UserAnswers p);
	
	/**
	 * to update an answer by a user to a question
	 * @param p
	 */
	public void updateUserAnswer(UserAnswers p);
}
