package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.beans.game.GameSessionBean;
import com.ex.services.GameManagerService;

@Controller
@RequestMapping("/start-game-connect")
@CrossOrigin(origins = "*")
public class StartGameConnectController {
	
	private static Logger logger = Logger.getLogger(StartGameController.class);
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public int startGameConnect(HttpServletRequest req, HttpServletResponse resp) {
		
		StringBuffer gameKey = new StringBuffer(req.getParameter("key"));
		
		GameManagerService manager = GameManagerService.getInstance();
		
		GameSessionBean game = manager.getGameByKey(gameKey);
		
		//Game is ready to start
		game.setState(1);
		
		return 0;
	}

}
