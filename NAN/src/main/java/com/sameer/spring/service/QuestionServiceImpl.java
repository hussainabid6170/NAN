package com.sameer.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sameer.spring.dao.QuestionsDAO;
import com.sameer.spring.model.Questions;

@Service
public class QuestionServiceImpl implements QuestionsService {
	
	@Autowired
	private QuestionsDAO questionsDAO;

	@Override
	@Transactional
	public List<Questions> listQuestion() {
		return questionsDAO.listQuestions();
	}

	@Override
	@Transactional
	public Questions getQuestionById(int id) {
		return questionsDAO.getQuestionById(id);
	}
}
