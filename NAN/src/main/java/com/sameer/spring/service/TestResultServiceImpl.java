package com.sameer.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sameer.spring.dao.TestResultsDAO;
import com.sameer.spring.model.TestResult;

@Service
public class TestResultServiceImpl implements TestResultService {
	
	@Autowired
	private TestResultsDAO	testResultsDAO;

	@Override
	@Transactional
	public void addTestResult(TestResult p) {
		testResultsDAO.addTestResult(p);
	}

	@Override
	@Transactional
	public void updateTestResult(TestResult p) {
		testResultsDAO.updateTestResult(p);
	}

	@Override
	@Transactional
	public TestResult getTestResultByUser(String userName) {
		return testResultsDAO.getTestResultByUser(userName);
	}

	@Override
	@Transactional
	public boolean checkTestValidationForUser(String userName, int duration) {
		boolean isValid = true;
		TestResult testResult = getTestResultByUser(userName);
		if(testResult != null){
			if("true".equalsIgnoreCase(testResult.getTestCompleated())){
				isValid = false;
			}else{
				long startTime = Long.parseLong(testResult.getStartTime());
				long currentTime = System.currentTimeMillis();
				if((startTime + (30 * 60 * 1000)) < currentTime){
					isValid = false;
				}
			}
		}
		return isValid;
	}
	@Override
	@Transactional
	public int getSecondsLeft(String userName, int duration) {
		int secondsleft = duration * 60;
		TestResult testResult = getTestResultByUser(userName);
		if(testResult != null){
			if(!"true".equalsIgnoreCase(testResult.getTestCompleated())){
				long startTime = Long.parseLong(testResult.getStartTime());
				long currentTime = System.currentTimeMillis();
				long elapsedTime = (currentTime - startTime) / 1000;
				if(elapsedTime < secondsleft){
					return (int) (secondsleft - elapsedTime);
				}else{
					secondsleft = 0;
				}
			}else{
				secondsleft = 0;
			}
		}
		return secondsleft;
	}
}
