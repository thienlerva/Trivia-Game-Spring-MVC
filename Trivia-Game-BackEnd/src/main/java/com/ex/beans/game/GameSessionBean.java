package com.ex.beans.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;

import com.ex.controllers.StartGameController;
import com.ex.services.LeaderboardService;


public class GameSessionBean {
	
	private static Logger logger = Logger.getLogger(StartGameController.class);
	
	QuestionBean currentQuestion;
	
	int currentAnswers;
	
	int currentQuestionIndex;

	long startTime;
	
	long currentTime;
	
	int instanceId;
	
	StringBuffer joinKey;
	
	public int numberOfQuestions;

	int state;
	
	StringBuffer globalChatBuffer;
	
	StringBuffer category;
	
	StringBuffer difficulty;
	
	StringBuffer name;
	
	StringBuffer scope;
	
	long roundTime = 20;
	
	public int count = 0;

	public ArrayList<PlayerBean> currentPlayers;
	
	int maxPlayers;
	
	int currentAnswerCounter;
	
	ArrayList<QuestionBean> Questions;
	
	public synchronized void setUserConnected(int id) {
		
		for(int i = 0; i < this.getCurrentPlayers().size(); i++) {
			if(this.getCurrentPlayers().get(i).getPlayerId() == id) {
				this.getCurrentPlayers().get(i).setConnected();
			}
		}
		
	}
	
	public synchronized void waitForConnection() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void startGame() {
		
		//this.waitForConnection();
		
		//Remove all players not connected
		/*
		for(int i = 0; i < this.getCurrentPlayers().size(); i++) {
			if(this.getCurrentPlayers().get(i).isConnected() == false) {
				this.getCurrentPlayers().remove(i);
			}
		}
		*/
		
		//Store questions
		this.Questions = ObjectMapperQuestion.getQuestions(this.numberOfQuestions, this.category.toString().toLowerCase());
		
		logger.trace(Questions);
		logger.trace(this.numberOfQuestions);
		logger.trace(this.category.toString());
		
		//Set the currentQuestion
		this.currentQuestion = this.Questions.get(this.currentQuestionIndex);
		
		//Cut off more players from joining
		this.maxPlayers = this.currentPlayers.size();
		
		// Reset timer to 20 seconds,
		// This happens last to prevent processing time to take up game time
		this.resetTimer();
	}
	
	public synchronized void updateGame() {
		
		logger.trace("Find me too");
		//logger.trace(this);
		logger.trace(this.getCurrentAnswerCounter());
		logger.trace(this.getCurrentTime());
		logger.trace(this.getCurrentPlayers().size());
		
		//If the timer ran out, reset the 
		if(this.getCurrentTime() == 0 || this.getCurrentPlayers().size() == this.getCurrentAnswerCounter()) {
			
			//If game is over
			if(this.Questions.size() == this.currentQuestionIndex + 1) {
				
				logger.trace("LOOK FOR ME");
				
				//End game state
				this.state = 2;
				
				LeaderboardService ls = new LeaderboardService();
				for (PlayerBean pb : currentPlayers)
				{
					HighScorePlayerBean hb = new HighScorePlayerBean(pb.getUsername().toString(),pb.getScore(),pb.getMaxStreak(),pb.getRightAnswers());
					ls.save(hb);
				}
				//Access repo service
				
				return;
			}
			
			//Get the new question
			this.loadNewQuestion();
			
			//Set the timer back to 20 seconds;
			this.resetTimer();
			
			return;
		}
		
		
		
		//Game is still going on, update time
		//Set current time
		long now = System.currentTimeMillis();
		this.setCurrentTime(this.roundTime - ((now - this.getStartTime())/1000));
		if(this.getCurrentTime() < 0) this.setCurrentTime(0);
				
	}

	public synchronized void loadNewQuestion() {
		
		//Load question
		this.currentQuestionIndex += 1;
		this.currentQuestion = this.Questions.get(this.currentQuestionIndex);
		
		this.currentAnswerCounter = 0;
	}
	
	public synchronized int getAnswerPosition() {
		this.setCurrentAnswerCounter(this.getCurrentAnswerCounter() + 1);
		return this.getCurrentAnswerCounter();
	}
	
	public synchronized void resetTimer() {
		//Set the current timer;
		this.setStartTime(System.currentTimeMillis());
		logger.trace(System.currentTimeMillis());
		long now = System.currentTimeMillis();
		this.setCurrentTime(this.roundTime - ((now - this.getStartTime())/1000));
		logger.trace("Find me");
		logger.trace(((now - this.getStartTime())/1000));
	}
	
	public synchronized QuestionBean getCurrentQuestion() {
		return currentQuestion;
	}

	public synchronized void setCurrentQuestion(QuestionBean currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public synchronized int getCurrentAnswers() {
		return currentAnswers;
	}

	public synchronized void setCurrentAnswers(int currentAnswers) {
		this.currentAnswers = currentAnswers;
	}

	public synchronized int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

	public synchronized void setCurrentQuestionIndex(int currentQuestionIndex) {
		this.currentQuestionIndex = currentQuestionIndex;
	}

	public synchronized long getStartTime() {
		return startTime;
	}

	public synchronized void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public synchronized long getCurrentTime() {
		return currentTime;
	}

	public synchronized void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

	public synchronized int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public synchronized void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public synchronized int getCount() {
		return count;
	}

	public synchronized void setCount(int count) {
		this.count = count;
	}

	public synchronized int getCurrentAnswerCounter() {
		return currentAnswerCounter;
	}

	public synchronized void setCurrentAnswerCounter(int currentAnswerCounter) {
		this.currentAnswerCounter = currentAnswerCounter;
	}

	public static synchronized Comparator<PlayerBean> getSortByPointsComparator() {
		return sortByPointsComparator;
	}

	public static synchronized void setSortByPointsComparator(Comparator<PlayerBean> sortByPointsComparator) {
		GameSessionBean.sortByPointsComparator = sortByPointsComparator;
	}
	
	public StringBuffer getJoinKey() {
		return joinKey;
	}

	public void setJoinKey(StringBuffer joinKey) {
		this.joinKey = joinKey;
	}
	
	public void getTopThreePlayers(){
		Collections.sort(this.getCurrentPlayers(), sortByPointsComparator);
	}
	
	public static Comparator<PlayerBean> sortByPointsComparator = new Comparator<PlayerBean>() {         
	    @Override         
	    public int compare(PlayerBean player1, PlayerBean player2) {             
	      return (player1.getScore() > player1.getScore() ? -1 :                     
	              (player1.getScore() == player1.getScore() ? 0 : 1));           
	    }     
	 };
	
	
	public synchronized void loadQuestions() {
		
		return;
		
	}
	
	public synchronized PlayerBean getPlayerById(int id) {
		for(int i = 0; i < this.currentPlayers.size(); i++) {
			if(this.currentPlayers.get(i).getPlayerId() == id) {
				return this.currentPlayers.get(i);
			}
		}
		return null;
	}
	
	public synchronized Boolean hasPlayers() {
		if(this.currentPlayers == null) return false;
		return true;
	}
	
	public synchronized void addDumbyData(){
		if(this.hasPlayers()) return;
		this.currentPlayers = new ArrayList<PlayerBean>();
		
		for(int i = 0; i < 5; i ++) {
			PlayerBean temp = new PlayerBean();
			temp.setUsername(new StringBuffer("Test " + i));
			this.currentPlayers.add(temp);
		}
	}
	
	public synchronized void removeUser(int key) {
		
		for(int i = 0; i < this.getCurrentPlayers().size(); i++) {
			
			if(this.getCurrentPlayers().get(i).getPlayerId() == key){
				this.getCurrentPlayers().remove(i);
				this.getCurrentPlayers().trimToSize();
			}
		}
	}
	
	public synchronized void addPlayer(PlayerBean p) {
		this.currentPlayers.add(p);
	}
	
	public synchronized int getInstanceId() {
		return instanceId;
	}


	public synchronized void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}


	public synchronized int getState() {
		return state;
	}


	public synchronized void setState(int state) {
		this.state = state;
	}


	public synchronized StringBuffer getGlobalChatBuffer() {
		return globalChatBuffer;
	}


	public synchronized void setGlobalChatBuffer(StringBuffer globalChatBuffer) {
		this.globalChatBuffer = globalChatBuffer;
	}


	public synchronized StringBuffer getCategory() {
		return category;
	}


	public synchronized void setCategory(StringBuffer category) {
		this.category = category;
	}


	public synchronized StringBuffer getDifficulty() {
		return difficulty;
	}


	public synchronized void setDifficulty(StringBuffer difficulty) {
		this.difficulty = difficulty;
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


	public synchronized ArrayList<PlayerBean> getCurrentPlayers() {
		return currentPlayers;
	}


	public synchronized void setCurrentPlayers(ArrayList<PlayerBean> currentPlayers) {
		this.currentPlayers = currentPlayers;
	}


	public synchronized int getMaxPlayers() {
		return maxPlayers;
	}


	public synchronized void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public int getNextAnswerPosition() {
		currentAnswerCounter = currentAnswerCounter + 1;
		return count;
	}
	
	public void resetNextAnswerPosition() {
		currentAnswerCounter = 0;
	}


	public synchronized ArrayList<QuestionBean> getQuestions() {
		return Questions;
	}


	public synchronized void setQuestions(ArrayList<QuestionBean> questions) {
		Questions = questions;
	}
	
	public synchronized void addDummyPlayer() {
		PlayerBean temp = new PlayerBean();
		temp.setUsername(new StringBuffer("Added" + count));
		count++;
		this.currentPlayers.add(temp);
	}
	
	public GameSessionBean() {
		
		this.currentPlayers = new ArrayList<PlayerBean>();
		
	}
}