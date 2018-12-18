package com.ex.beans.game;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Entity //registers class as entity in DB
@Table(name="HighScorePlayerBean")//allows further configuration of Table in DB
public class HighScorePlayerBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id //necessary for Hibernate to identify objects
	@Column(name="ID")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
//@SequenceGenerator(name="U_SEQ_GEN", sequenceName="U_SEQ")
//@GeneratedValue(generator="U_SEQ_GEN", strategy=GenerationType.SEQUENCE)
	private int id;
	
	@Column(nullable=false, unique=true, name="USERNAME")
	private String username;
	
	@Column(nullable=false, name="SCORE")
	private int score;
	
	@Column(nullable=false, name="MAX_STREAK")
	private int max_streak;
	
	@Column(nullable=false, name="NUMBER_CORRECT")
	private int number_correct;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "HighScoreBean [id=" + id + ", username=" + username + ", score=" + score + "]";
	}
	public HighScorePlayerBean(String username, int score, int max_streak, int number_correct) {
		super();
		this.username = username;
		this.score = score;
		this.max_streak = max_streak;
		this.number_correct = number_correct;
	}
	public HighScorePlayerBean() {
		
	}
	public int getMaxStreak() {
		return max_streak;
	}
	public void setMaxStreak(int max_streak) {
		this.max_streak = max_streak;
	}
	public int getRightAnswers() {
		return number_correct;
	}
	public void setRightAnswers(int number_correct) {
		this.number_correct = number_correct;
	}
	
}
