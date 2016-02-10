package com.sameer.spring.dao;

import java.util.ArrayList;
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
import com.sameer.spring.model.UserAnswers;

@Repository
public class UserAnswersDAOImpl implements UserAnswersDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAnswersDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addUserAnswer(UserAnswers p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(p);
		logger.info("User answer saved successfully");
	}

	@Override
	public void updateUserAnswer(UserAnswers p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
	}

	@Override
	public List<UserAnswers> listAnswersByUserName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserAnswers> userAnswers = new ArrayList<UserAnswers>();
		try {
			Criteria criteria = session.createCriteria(UserAnswers.class);
			criteria.add(Restrictions.like("userName", userName));
			userAnswers =  criteria.list();
		} catch (SQLGrammarException sg) {
			logger.debug("SQLGrammarException occured in listAnswersByUserName method::", sg);
		} catch (HibernateException he) {
			logger.debug("HibernateException occured in listAnswersByUserName method::", he);
		} catch (Exception e) {
			logger.debug("Exception occured in listAnswersByUserName method::", e);
		}
		return userAnswers;
	}

	@Override
	public UserAnswers getAnswerByIdAndUserName(int questionId, String userName) {

		Session session = this.sessionFactory.getCurrentSession();
		UserAnswers model = null;
		try {
			Criteria criteria = session.createCriteria(UserAnswers.class);
			criteria.add(Restrictions.like("questionId", questionId));
			criteria.add(Restrictions.like("userName", userName));
			model = (UserAnswers) criteria.uniqueResult();
		} catch (SQLGrammarException sg) {
			logger.debug("SQLGrammarException occured in getAnswerByIdAndUserName method::", sg);
		} catch (HibernateException he) {
			logger.debug("HibernateException occured in getAnswerByIdAndUserName method::", he);
		} catch (Exception e) {
			logger.debug("Exception occured in getAnswerByIdAndUserName method::", e);
		}
		return model;
	}

}
