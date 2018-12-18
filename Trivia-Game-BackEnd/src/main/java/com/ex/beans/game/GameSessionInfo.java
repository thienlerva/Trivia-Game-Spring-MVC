package com.ex.beans.game;

import java.util.ArrayList;

public class GameSessionInfo {
	
	private StringBuffer category;
	private int players;
	private int maxPlayers;
	private StringBuffer name;
	private StringBuffer scope;
	private StringBuffer difficulty;
	private StringBuffer key;
	private QuestionBean currentQuestion;
	private int numberOfAnswers;
	private long currentCountDown;
	private ArrayList<PlayerBean> topScores;
	private int numberOfQuestions;
	private int currentQuestionNumber;
	public int status;
	
	public GameSessionInfo(GameSessionBean game) {
		this.category = game.getCategory();
		this.players = game.getCurrentPlayers().size();
		this.maxPlayers = game.getMaxPlayers();
		this.name = game.getName();
		this.scope = game.getScope();
		this.difficulty = game.getDifficulty();
		this.key = game.getJoinKey();
		this.currentQuestion = game.getCurrentQuestion();
		this.numberOfAnswers = game.getCurrentAnswerCounter();
		this.currentCountDown = game.getCurrentTime();
		this.numberOfQuestions = game.getNumberOfQuestions();
		this.currentQuestionNumber = game.getCurrentQuestionIndex() + 1;
		this.status = game.state;
	}
	
	public GameSessionInfo() {
		
	}
	
	public synchronized QuestionBean getCurrentQuestion() {
		return currentQuestion;
	}

	public synchronized void setCurrentQuestion(QuestionBean currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public synchronized int getNumberOfAnswers() {
		return numberOfAnswers;
	}

	public synchronized void setNumberOfAnswers(int numberOfAnswers) {
		this.numberOfAnswers = numberOfAnswers;
	}

	public synchronized long getCurrentCountDown() {
		return currentCountDown;
	}

	public synchronized void setCurrentCountDown(long currentCountDown) {
		this.currentCountDown = currentCountDown;
	}

	public synchronized ArrayList<PlayerBean> getTopScores() {
		return topScores;
	}

	public synchronized void setTopScores(ArrayList<PlayerBean> topScores) {
		this.topScores = topScores;
	}

	public synchronized int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public synchronized void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public synchronized int getCurrentQuestionNumber() {
		return currentQuestionNumber;
	}

	public synchronized void setCurrentQuestionNumber(int currentQuestionNumber) {
		this.currentQuestionNumber = currentQuestionNumber;
	}
	
	public StringBuffer getKey() {
		return key;
	}
	public void setKey(StringBuffer key) {
		this.key = key;
	}
	public synchronized StringBuffer getDifficulty() {
		return difficulty;
	}
	public synchronized void setDifficulty(StringBuffer difficulty) {
		this.difficulty = difficulty;
	}
	public synchronized StringBuffer getCategory() {
		return category;
	}
	public synchronized void setCategory(StringBuffer category) {
		this.category = category;
	}
	public synchronized int getPlayers() {
		return players;
	}
	public synchronized void setPlayers(int players) {
		this.players = players;
	}
	public synchronized int getMaxPlayers() {
		return maxPlayers;
	}
	public synchronized void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public synchronized StringBuffer getName() {
		return name;
	}
	public synchronized void setName(StringBuffer name) {
		this.name = name;
	}
	public synchronized StringBuffer getScope() {
		return scope;
	}
	public synchronized void setScope(StringBuffer scope) {
		this.scope = scope;
	}

}
