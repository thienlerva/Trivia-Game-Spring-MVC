package com.ex.controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.beans.game.GameSessionBean;
import com.ex.beans.game.PlayerBean;
import com.ex.beans.game.WaitingMessage;
import com.ex.services.GameManagerService;

@Controller
@CrossOrigin(origins = "*")
@MessageMapping("/waiting-update")
public class WaitingController {
	
	private static Logger logger = Logger.getLogger(WaitingController.class);
	
	private static int count = 1;
	
	@MessageMapping("{lobbyId}/update-waiting")
	@SendTo("/waiting/{lobbyId}/send-waiting")
	@CrossOrigin(origins = "*")
	@ResponseBody
	public WaitingMessage connect(@DestinationVariable String lobbyId,SimpMessageHeaderAccessor headerAccessor) {
		
		logger.trace("Updating");
		
		GameManagerService gm = GameManagerService.getInstance();
		
		logger.trace(gm.gameList.size());
		
		GameSessionBean game = gm.getGameByKey(new StringBuffer(lobbyId));
		
		logger.trace(game);
		
		//game.addDumbyData();
		logger.trace("Is this it");
		
		//game.addDummyPlayer();
		
		ArrayList<PlayerBean> playerList = game.getCurrentPlayers();
		WaitingMessage wm = new WaitingMessage();
		
		int status = game.getState();
		
		wm.setPlayers(playerList);
		wm.setStatus(status);
		
		return wm;
	}
		

}
