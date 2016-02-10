package com.sameer.spring.dao;

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

import com.sameer.spring.model.TestResult;

@Repository
public class TestResultsDAOImpl implements TestResultsDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TestResultsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void addTestResult(TestResult p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(p);
		logger.info("User answer saved successfully");
	}

	@Override
	public void updateTestResult(TestResult p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
	}

	@Override
	public TestResult getTestResultByUser(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		TestResult model = null;
		try {
			Criteria criteria = session.createCriteria(TestResult.class);
			criteria.add(Restrictions.like("userId", userName));
			model = (TestResult) criteria.uniqueResult();
		} catch (SQLGrammarException sg) {
			logger.debug("SQLGrammarException occured in getTestResultByUser method::", sg);
		} catch (HibernateException he) {
			logger.debug("HibernateException occured in getTestResultByUser method::", he);
		} catch (Exception e) {
			logger.debug("Exception occured in getTestResultByUser method::", e);
		}
		return model;
	}

}
