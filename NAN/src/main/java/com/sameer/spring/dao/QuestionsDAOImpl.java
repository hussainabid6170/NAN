package com.sameer.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sameer.spring.model.Questions;
import com.sameer.spring.model.TestDetails;

@Repository
public class QuestionsDAOImpl implements QuestionsDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Questions> listQuestions() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Questions> questionssList = session.createQuery("from Questions").list();
		return questionssList;
	}

	@Override
	public Questions getQuestionById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Questions model = null;
		try {
			Criteria criteria = session.createCriteria(Questions.class);
			criteria.add(Restrictions.like("id", id));
			model = (Questions) criteria.uniqueResult();
		} catch (SQLGrammarException sg) {
			logger.debug("SQLGrammarException occured in getQuestionById method::", sg);
		} catch (HibernateException he) {
			logger.debug("HibernateException occured in getQuestionById method::", he);
		} catch (Exception e) {
			logger.debug("Exception occured in getQuestionById method::", e);
		}
		return model;

	}

}
