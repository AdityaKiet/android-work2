package com.exam.dto;

public class QuizMasterDTO {
	private String quiz_id;
	private String quiz_name;
	public String getQuiz_id() {
		return quiz_id;
	}
	public void setQuiz_id(String quiz_id) {
		this.quiz_id = quiz_id;
	}
	public String getQuiz_name() {
		return quiz_name;
	}
	public void setQuiz_name(String quiz_name) {
		this.quiz_name = quiz_name;
	}
	@Override
	public String toString() {
		return "QuizMasterDTO [quiz_id=" + quiz_id + ", quiz_name=" + quiz_name
				+ "]";
	}
	
}
