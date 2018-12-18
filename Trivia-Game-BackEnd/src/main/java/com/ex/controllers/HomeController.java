package com.ex.controllers;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/home")
public class HomeController {
	
	@RequestMapping(method=RequestMethod.GET, value="/test")
	public String home(HttpServletResponse resp, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		
		resp.setStatus(404);
		
		return "Welcome to Spring2!";
	}

}