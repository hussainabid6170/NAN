package com.sameer.spring.dao;

import java.util.List;

import com.sameer.spring.model.Questions;

public interface QuestionsDAO {
	public List<Questions> listQuestions();
	public Questions getQuestionById(int id);
}
