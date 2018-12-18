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
@RequestMapping("/create-game")
@CrossOrigin(origins = "*")
public class CreateGameController {
	
	private static Logger logger = Logger.getLogger(CreateGameController.class);
	static int count = 1185;
        
	@CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JoinLobbyResponse createGame(HttpServletRequest req, HttpServletResponse resp) {
        GameManagerService service = GameManagerService.getInstance();
        int id = service.createGame();
        logger.trace(service.gameList.size());
        GameSessionBean gs = service.getGame(id);
        
        gs.setJoinKey(new StringBuffer(count + ""));
        gs.setCategory(new StringBuffer(req.getParameter("category").toLowerCase()));
        gs.setMaxPlayers(new Integer(req.getParameter("seats")));
        gs.numberOfQuestions = new Integer(req.getParameter("questions"));
        gs.setName(new StringBuffer(req.getParameter("name")));
        
        logger.trace(gs.getJoinKey().toString());
        
        StringBuffer username = new StringBuffer(req.getParameter("username"));
        
        PlayerBean player = new PlayerBean();
        
        HttpSession session = req.getSession();
        session.setAttribute("playerId", gs.count);
        session.setAttribute("lobbyId", count + "");
        
        gs.count = gs.count + 1;
        player.setUsername(username);
        player.setPlayerId(gs.count);
        
        gs.addPlayer(player);
        
        JoinLobbyResponse message = new JoinLobbyResponse();
        
        message.setUserId(player.getPlayerId());
        message.setLobbyId(new StringBuffer(count + ""));
        message.setCategory(new StringBuffer(req.getParameter("category")));
        message.setLobbyName(new StringBuffer(req.getParameter("name")));
        message.setQuestions(new Integer(req.getParameter("questions")));
        
        count = count + 1;

        return message;
    }
}
