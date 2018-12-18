package com.ex.beans.game;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperQuestion {
	
	
	//Turn JSON into question arrayList 
	public static void main(String[] args) {
		
		System.out.println(getQuestions(10, "All"));
		
	}
	

	public static  ArrayList<QuestionBean> getQuestions(int numOfQuestions, String category) {	
		// Will populate list with questions fetched from the API
		ArrayList<QuestionBean> questionList = new ArrayList<QuestionBean>();
		
		try {
			// Generate URL to fetch questions from API
			String urlString;
			int categoryNumber = convertCategoryToInt(category);		
			if(categoryNumber == 0) {	// Corresponds to "any" category
				urlString = "https://opentdb.com/api.php?amount=" + numOfQuestions;
			} else {
				urlString = "https://opentdb.com/api.php?amount=" + numOfQuestions 
						+ "&category=" + categoryNumber;
			}
			
			// Retrieve JSON string from API and map it to a "data transfer object"
			// This APIJson includes the response code
			APIJson objJson = readJsonWithObjectMapper(urlString);
			// Get the JSON associated with only the array of questions (without the response code)
			QuestionJson[] question = objJson.getResults();
			
			// Convert each question to a QuestionBean and add to questionList			
			for(QuestionJson q : question ) {
				QuestionBean qb = createQuestionBean(q);
				questionList.add(qb);
			}		
			//System.out.println(questionList);		
		} catch (IOException e) {			
			e.printStackTrace();
		}	
		return questionList;
		
	}

	// Read JSON from Trivia API and convert it into a "data transfer" object
	public static APIJson readJsonWithObjectMapper(String urlString) throws IOException {
		
//		String hardcode = "{\"response_code\":0,\"results\":[{\"category\":\"General Knowledge\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"Which sign of the zodiac is represented by the Crab?\",\"correct_answer\":\"Cancer\",\"incorrect_answers\":[\"Libra\",\"Virgo\",\"Sagittarius\"]},{\"category\":\"History\",\"type\":\"multiple\",\"difficulty\":\"easy\",\"question\":\"The collapse of the Soviet Union took place in which year?\",\"correct_answer\":\"1991\",\"incorrect_answers\":[\"1992\",\"1891\",\"1990\"]},{\"category\":\"Entertainment: Music\",\"type\":\"boolean\",\"difficulty\":\"medium\",\"question\":\"Ashley Frangipane performs under the stage name Halsey.\",\"correct_answer\":\"True\",\"incorrect_answers\":[\"False\"]},{\"category\":\"Entertainment: Video Games\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"What is the fastest speed possible in Trackmania&sup2;: Stadium?\",\"correct_answer\":\"1000  km\\/h\",\"incorrect_answers\":[\"500 km\\/h\",\"320 km\\/h\",\"100 km\\/h\"]},{\"category\":\"Entertainment: Video Games\",\"type\":\"boolean\",\"difficulty\":\"easy\",\"question\":\"The ultimate phrase used by Pharah from Overwatch is: &quot;Justice rains from above!&quot;\",\"correct_answer\":\"True\",\"incorrect_answers\":[\"False\"]},{\"category\":\"History\",\"type\":\"boolean\",\"difficulty\":\"medium\",\"question\":\"Adolf Hitler was accepted into the Vienna Academy of Fine Arts.\",\"correct_answer\":\"False\",\"incorrect_answers\":[\"True\"]},{\"category\":\"Geography\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"The Andaman and Nicobar Islands in South East Asia are controlled by which country?\",\"correct_answer\":\"India\",\"incorrect_answers\":[\"Vietnam\",\"Thailand\",\"Indonesia\"]},{\"category\":\"Entertainment: Video Games\",\"type\":\"boolean\",\"difficulty\":\"medium\",\"question\":\"In &quot;League of Legends&quot;, there exists four different types of Dragon.\",\"correct_answer\":\"False\",\"incorrect_answers\":[\"True\"]},{\"category\":\"Geography\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"What is the capital of Mauritius?\",\"correct_answer\":\"Port Louis\",\"incorrect_answers\":[\"Port Moresby\",\"Port Vila\",\"Port-au-Prince\"]},{\"category\":\"Entertainment: Books\",\"type\":\"multiple\",\"difficulty\":\"hard\",\"question\":\"In the Magic: The Gathering universe,  the Antiquities, Ice Age, and Alliances expansions take place on which continent?\",\"correct_answer\":\"Terisiare\",\"incorrect_answers\":[\"Aerona\",\"Shiv\",\"Jamuraa\"]}]}";
		
		// Create a URL object from the String
		URL url = new URL(urlString);
		ObjectMapper mapper = new ObjectMapper();
		APIJson obj = mapper.readValue(url, APIJson.class);
//		APIJson obj = mapper.readValue(hardcode, APIJson.class);
		return obj;
	}

	// Convert category string name to its corresponding category number according to the API
	public static int convertCategoryToInt(String category) {
		
		switch(category) {
		case "geography": return 22;
		case "history": return 23;
		case "sports": return 21;
		case "politics": return 24;
		case "art": return 25;
		case "math": return 19;
		case "computer": return 18;
		case "movies": return 11;
		case "all": return 0;	
		default: 
			System.out.println("wrong category");	// Should throw an exception here
			return 1;
		}
	}

	/**
	 * Populates a QuestionBean in the correct usable game format
	 */
	public static QuestionBean createQuestionBean(QuestionJson qj) {
		QuestionBean question = new QuestionBean();
		question.setCategory(new StringBuffer(qj.getCategory().toLowerCase()));
		if (qj.getType().equals("multiple")) {
			question.setMultipleChoice(true);
		}
		else {
			question.setMultipleChoice(false);
		}
		question.setDifficulty(new StringBuffer(qj.getDifficulty()));
		question.setQuestion(new StringBuffer(qj.getQuestion()));
		question.addAnswer(new StringBuffer(qj.getCorrect_answer())); 	// Add correct answer
		for (String a : qj.getIncorrect_answers()) {
			question.addAnswer(new StringBuffer(a));					// Add incorrect answers
		}
		question.randomizeAnswers();   									// Randomize the answers vector
		int i = 0;
		for (StringBuffer a : question.getAnswers()) {					// Set index of correct answer
			if (qj.getCorrect_answer().equals(a.toString())) {
				question.setCorrectIndex(i);
				break;
			}
			else {
				i++;
			}
		}		
		return question;
	}

}
