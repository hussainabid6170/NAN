package com.sameer.spring.service;

import com.sameer.spring.model.TestResult;

public interface TestResultService {
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
	/**
	 * this method will check validation of test for user
	 * @param userName
	 * @param duration 
	 * @return
	 */
	public boolean checkTestValidationForUser(String userName, int duration);
	/**
	 * this method will return the seconds left in test
	 * @param userName
	 * @param duration
	 * @return
	 */
	int getSecondsLeft(String userName, int duration);
}
