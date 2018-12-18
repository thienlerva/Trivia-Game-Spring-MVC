package com.ex.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.ex.beans.game.HighScorePlayerBean;
import com.ex.repository.HighScoreBeanRepository;



@Component
public class App {
	
	@Autowired
	private static HighScoreBeanRepository repo;
	
	

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		HighScoreBeanRepository repo = (HighScoreBeanRepository) context.getBean(HighScoreBeanRepository.class);
		
		
	
		HighScorePlayerBean hspb = new HighScorePlayerBean();

		hspb.setUsername("John");
		hspb.setScore(10);
		hspb.setMaxStreak(10);
		hspb.setRightAnswers(10);
	
		repo.save(hspb);
		
		List<HighScorePlayerBean> u = repo.findAll();
		System.out.println(u);
	
		
		
		
		
	}

}
