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

import com.sameer.spring.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getPersonById(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		User model = null;
		try {
		    Criteria criteria = session.createCriteria(User.class);
		    criteria.add(Restrictions.like("username", userName));
		    model = (User) criteria.uniqueResult();
		} catch (SQLGrammarException sg) {
		  	logger.debug("SQLGrammarException occured in findByPrimaryKey method::", sg);
		} catch (HibernateException he) {
		    	logger.debug("HibernateException occured in findByPrimaryKey method::", he);
		    } catch (Exception e) {
		    	logger.debug("Exception occured in findByPrimaryKey method::", e);
		    }
		    return model;
	}

}
