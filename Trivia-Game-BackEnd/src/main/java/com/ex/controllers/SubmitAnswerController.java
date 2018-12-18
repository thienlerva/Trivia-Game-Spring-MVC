package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.beans.game.GameSessionBean;
import com.ex.beans.game.PlayerBean;
import com.ex.services.GameManagerService;

@Controller
@RequestMapping("/game-update")
@CrossOrigin(origins = "*")
public class SubmitAnswerController {
	
	private static Logger logger = Logger.getLogger(NewUserController.class);
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public int submitAnswer(HttpServletRequest req, HttpServletResponse resp) {
		
		int playerId = Integer.parseInt(req.getParameter("playerId"));
		StringBuffer lobbyKey = new StringBuffer(req.getParameter("lobbyKey"));
		int points = Integer.parseInt(req.getParameter("points"));
		
		GameManagerService gm = GameManagerService.getInstance();
		GameSessionBean game = gm.getGameByKey(lobbyKey);
		PlayerBean player = game.getPlayerById(playerId);
		
		player.setCurrentAnswerPosition(game.getNextAnswerPosition());
		
		//Add the points to the score
		player.setScore(player.getScore() + points);
		
		//If player answered the question right
		if(points != 0) {
			player.setRightAnswers(player.getRightAnswers() + 1);
			player.setCurrentStreak(player.getCurrentStreak() + 1);
			if(player.getMaxStreak() < player.getCurrentStreak()) {
				player.setMaxStreak(player.getCurrentStreak());
			}
		//If player answered the question wrong
		} else {
			player.setWrongAnswers(player.getWrongAnswers() + 1);
			player.setCurrentStreak(0);
		}
		
		
		
		return 0;
	}

}
