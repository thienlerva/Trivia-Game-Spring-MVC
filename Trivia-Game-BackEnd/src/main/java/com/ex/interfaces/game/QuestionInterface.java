package com.ex.interfaces.game;

public interface QuestionInterface {
	
	public Boolean checkAnswer(int answer);
	public void setAnswer(int answer);
	public int getAnswer();
	public void setOption(StringBuilder answer, int place);

}