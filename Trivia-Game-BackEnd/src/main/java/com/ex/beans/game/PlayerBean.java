package com.ex.beans.game;

import org.apache.log4j.Logger;

import com.ex.controllers.StartGameController;

public class PlayerBean {
	
	private static Logger logger = Logger.getLogger(PlayerBean.class);
	
	//Thread safe username
	private StringBuffer username;
	
	private int playerId;
	
	public synchronized int getPlayerId() {
		return playerId;
	}

	public synchronized void setPlayerId(int playerId) {
		logger.trace("User Find");
		logger.trace(playerId);
		this.playerId = playerId;
	}

	//0 = a, 1 = b, 2 = c, 3 = d
	private int currentAnswer;
	
	//the order in which the usere answered the question
	private int currentAnswerPosition;
	
	private int score;
	
	private int maxStreak;
	
	private int rightAnswers;
	
	private int wrongAnswers;
	
	private Boolean connected = false;
	
	public synchronized Boolean isConnected() {
		return this.connected;
	}
	
	public synchronized void setConnected() {
		this.connected = true;
	}
	
	public synchronized int getCurrentStreak() {
		return currentStreak;
	}

	public synchronized void setCurrentStreak(int currentStreak) {
		this.currentStreak = currentStreak;
	}

	private int currentStreak;

	public synchronized StringBuffer getUsername() {
		return username;
	}

	public synchronized void setUsername(StringBuffer username) {
		this.username = username;
	}

	public synchronized int getCurrentAnswer() {
		return currentAnswer;
	}

	public synchronized void setCurrentAnswer(int currentAnswer) {
		this.currentAnswer = currentAnswer;
	}

	public synchronized int getCurrentAnswerPosition() {
		return currentAnswerPosition;
	}

	public synchronized void setCurrentAnswerPosition(int currentAnswerPosition) {
		this.currentAnswerPosition = currentAnswerPosition;
	}

	public synchronized int getScore() {
		return score;
	}

	public synchronized void setScore(int score) {
		this.score = score;
	}

	public synchronized int getMaxStreak() {
		return maxStreak;
	}

	public synchronized void setMaxStreak(int maxStreak) {
		this.maxStreak = maxStreak;
	}

	public synchronized int getRightAnswers() {
		return rightAnswers;
	}

	public synchronized void setRightAnswers(int rightAnswers) {
		this.rightAnswers = rightAnswers;
	}

	public synchronized int getWrongAnswers() {
		return wrongAnswers;
	}

	public synchronized void setWrongAnswers(int wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}
}
