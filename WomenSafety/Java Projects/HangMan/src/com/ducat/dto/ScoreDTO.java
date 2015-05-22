package com.ducat.dto;

public class ScoreDTO {
	String user;
	int sr;
	int score;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSr() {
		return sr;
	}
	public void setSr(int sr) {
		this.sr = sr;
	}
	@Override
	public String toString() {
		return "ScoreDTO [user=" + user + ", sr=" + sr + ", score=" + score
				+ "]";
	}
	
	

}
