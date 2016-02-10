package com.sameer.spring.dao;

import java.util.List;

import com.sameer.spring.model.TestResult;
import com.sameer.spring.model.UserAnswers;

public interface TestResultsDAO {
	/**
	 * 
	 * @param p
	 */
	public void addTestResult(TestResult p);
	/**
	 * 
	 * @param p
	 */
	public void updateTestResult(TestResult p);
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public TestResult getTestResultByUser(String userName);
}
