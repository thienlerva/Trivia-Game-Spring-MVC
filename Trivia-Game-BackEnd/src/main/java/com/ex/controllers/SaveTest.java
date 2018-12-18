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
@RequestMapping("/save")
public class SaveTest {

	@CrossOrigin(origins = "*")
	@RequestMapping(method=RequestMethod.GET)
	public void save() {
		LeaderboardService ls = new LeaderboardService();
		ls.save(new HighScorePlayerBean("ian", 10,3,10));
	}
}
