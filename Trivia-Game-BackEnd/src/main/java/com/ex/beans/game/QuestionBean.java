package com.ex.beans.game;

import java.util.Collections;
import java.util.Vector;

import org.springframework.stereotype.Component;

@Component
public class QuestionBean {
	
	private boolean isMultipleChoice; 
	private StringBuffer category;
	private StringBuffer difficulty;
	private StringBuffer question;
	private int correctIndex;
	private Vector<StringBuffer> answers;
	
	//payload['isMultipleChoice'];
	//payload['answers'][0][?]
	
	
	QuestionBean(){
		answers = new Vector<StringBuffer>();
	}
	
	public void randomizeAnswers() {
		Collections.shuffle(answers);
	}

	public boolean isMultipleChoice() {
		return isMultipleChoice;
	}

	public void setMultipleChoice(boolean isMultipleChoice) {
		this.isMultipleChoice = isMultipleChoice;
	}

	public StringBuffer getCategory() {
		return category;
	}

	public void setCategory(StringBuffer category) {
		this.category = category;
	}

	public StringBuffer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(StringBuffer difficulty) {
		this.difficulty = difficulty;
	}

	public StringBuffer getQuestion() {
		return question;
	}

	public void setQuestion(StringBuffer question) {
		this.question = question;
	}

	public int getCorrectIndex() {
		return correctIndex;
	}

	public void setCorrectIndex(int correctIndex) {
		this.correctIndex = correctIndex;
	}

	public Vector<StringBuffer> getAnswers() {
		return answers;
	}

	public void setAnswers(Vector<StringBuffer> answers) {
		this.answers = answers;
	}
	
	public void addAnswer(StringBuffer a) {
		this.answers.add(a);
	}

	@Override
	public String toString() {
		return "QuestionBean [isMultipleChoice=" + isMultipleChoice + ", category=" + category + ", difficulty="
				+ difficulty + ", question=" + question + ", correctIndex=" + correctIndex + ", answers=" + answers
				+ "]";
	}
	

}

