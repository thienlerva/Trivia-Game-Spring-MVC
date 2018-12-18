package com.ex.beans.game;

import java.util.Arrays;

public class QuestionJson {
	
	private String category;
	private String type;
	private String difficulty;
	private String question;
	private String correct_answer;
	private String[] incorrect_answers;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCorrect_answer() {
		return correct_answer;
	}
	public void setCorrect_answer(String correct_answer) {
		this.correct_answer = correct_answer;
	}
	public String[] getIncorrect_answers() {
		return incorrect_answers;
	}
	public void setIncorrect_answers(String[] incorrect_answers) {
		this.incorrect_answers = incorrect_answers;
	}
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n--------Question JSON--------\n");
		sb.append("Category: " + getCategory() + "\n");
		sb.append("Type: " + getType() + "\n");
		sb.append("Difficulty: " + getDifficulty() + "\n");
		sb.append("Question: " + getQuestion() + "\n");
		sb.append("Correct answer: " + getCorrect_answer() + "\n");
		sb.append("Incorrect answer: " + Arrays.toString(getIncorrect_answers()) + "\n");
		return sb.toString();
	}


}
