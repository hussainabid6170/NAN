package com.sameer.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author smisger
 *
 */
@Entity
@Table(name="test_results")
public class TestResult {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="test_start_time")
	private String startTime;
	
	@Column(name="test_finish_time")
	private String finishTime;
	
	@Column(name="test_compleated")
	private String testCompleated;
	
	@Column(name="score")
	private int score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getTestCompleated() {
		return testCompleated;
	}

	public void setTestCompleated(String testCompleated) {
		this.testCompleated = testCompleated;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
