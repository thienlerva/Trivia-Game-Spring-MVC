package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ex.beans.game.GameSessionBean;
import com.ex.beans.game.JoinLobbyResponse;
import com.ex.beans.game.PlayerBean;
import com.ex.services.GameManagerService;

@RestController
@RequestMapping("/join-waiting")
@CrossOrigin(origins = "*")
public class JoinLobbyController {
	
	private static Logger logger = Logger.getLogger(JoinLobbyController.class);
	
	static int count = 0;
        
	@CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JoinLobbyResponse joinGame(HttpServletRequest req, HttpServletResponse resp) {
		
		logger.trace("Joining Lobby");
		
		JoinLobbyResponse message = new JoinLobbyResponse();
		
		String key = req.getParameter("lobbyKey");
		
		GameManagerService gm = GameManagerService.getInstance();
		
		logger.trace(gm.gameList.size());
		
		GameSessionBean game = gm.getGameByKey(new StringBuffer(key));
		
		logger.trace(gm.gameList.size());
		
		if(game.currentPlayers.size() == game.getMaxPlayers()) {
			message.setHasError(1);
			message.setError(new StringBuffer("lobby is full"));
			resp.setStatus(HttpServletResponse.SC_CONFLICT);
		} else {
			PlayerBean newPlayer = new PlayerBean();
			game.count = game.count + 1;
			newPlayer.setPlayerId(game.count);
			newPlayer.setUsername(new StringBuffer(req.getParameter("username")));
			game.addPlayer(newPlayer);
			
			message.setUserId(newPlayer.getPlayerId());
			message.setLobbyId(game.getJoinKey());
	        message.setCategory(game.getCategory());
	        message.setLobbyName(game.getName());
	        message.setQuestions(game.numberOfQuestions);
	        
	        HttpSession session = req.getSession();
	        session.setAttribute("playerId", game.count);
	        session.setAttribute("lobbyId", count + "");
	        
		}

		return message;
	}
}
