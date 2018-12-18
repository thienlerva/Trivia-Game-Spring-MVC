package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ex.beans.game.GameSessionBean;
import com.ex.services.GameManagerService;


@Controller
@RequestMapping("/disconnectdfdfd")
@CrossOrigin(origins = "*")
public class DisconnectController {
	
	private static Logger logger = Logger.getLogger(DisconnectController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public void handleWebsocketDisconnectListener(HttpServletResponse resp, HttpServletRequest req) {
		
		logger.trace("DISCONNECT");
		
		GameManagerService manager = GameManagerService.getInstance();

		HttpSession session = req.getSession();
		
		if(session == null) return;
		
		int userId = (Integer) session.getAttribute("playerId");
		String lobbyId = (String) session.getAttribute("lobbyId");
		
		if(userId == 0 || lobbyId == null) return;
		
		GameSessionBean game = manager.getGameByKey(new StringBuffer(lobbyId));
		
		if(game == null) return;
		
		game.removeUser(userId);
	}

}
