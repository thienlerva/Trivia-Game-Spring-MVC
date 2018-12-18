package com.ex.beans.game;

import java.util.ArrayList;

public class GameUpdateResponse {
	
	QuestionBean question;
	int secondsLeft;
	ArrayList<PlayerBean> topPlayers;
	int answersCount;
	int currentQuestionCount;
	int maxQuestionCount;
}
