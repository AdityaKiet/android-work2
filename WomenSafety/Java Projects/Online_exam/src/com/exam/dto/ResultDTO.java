package com.exam.dto;

public class ResultDTO {
	private String user_id;
	private String quiz_id;
	private int score;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getQuiz_id() {
		return quiz_id;
	}
	public void setQuiz_id(String quiz_id) {
		this.quiz_id = quiz_id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "ResultDTO [user_id=" + user_id + ", quiz_id=" + quiz_id
				+ ", score=" + score + "]";
	}
	
}
