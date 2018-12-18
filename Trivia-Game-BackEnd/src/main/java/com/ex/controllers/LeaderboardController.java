package com.ex.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ex.beans.game.HighScorePlayerBean;
import com.ex.services.LeaderboardService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/leaders")
public class LeaderboardController {

	
	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.GET)
	public List<HighScorePlayerBean> getAll() {
		LeaderboardService ls = new LeaderboardService();
		return ls.getAll();
	}
	
}
