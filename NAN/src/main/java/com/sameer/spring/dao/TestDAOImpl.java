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

import com.sameer.spring.model.TestDetails;

@Repository
public class TestDAOImpl implements TestDAO {

	private static final Logger logger = LoggerFactory.getLogger(TestDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public TestDetails getTestById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TestDetails model = null;
		try {
			Criteria criteria = session.createCriteria(TestDetails.class);
			criteria.add(Restrictions.like("id", id));
			model = (TestDetails) criteria.uniqueResult();
		} catch (SQLGrammarException sg) {
			logger.debug("SQLGrammarException occured in getTestById method::", sg);
		} catch (HibernateException he) {
			logger.debug("HibernateException occured in getTestById method::", he);
		} catch (Exception e) {
			logger.debug("Exception occured in getTestById method::", e);
		}
		return model;

	}

}
