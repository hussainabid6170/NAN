package com.sameer.spring.service;

import java.util.List;

import com.sameer.spring.model.Questions;

public interface QuestionsService {
	public List<Questions> listQuestion();
	public Questions getQuestionById(int id);
}
