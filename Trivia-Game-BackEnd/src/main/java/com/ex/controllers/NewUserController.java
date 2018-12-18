package com.ex.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.services.GameManagerService;


@Controller
@RequestMapping("/new-user")
@CrossOrigin(origins = "*")
public class NewUserController {
	
	// This creates the "VERBOSE" level if it does not exist yet.
	final Level VERBOSE = Level.toLevel("VERBOSE");
	
	private static Logger logger = Logger.getLogger(NewUserController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	@CrossOrigin(origins = "*")
	public HttpServletResponse newUser(HttpServletResponse resp, HttpServletRequest req) {
		
		logger.trace("Hello");
		
		//No matter what create a new session if they call this servlet
		HttpSession session = req.getSession(false);
		if (session == null) {
			logger.trace("We got work");
		    session = req.getSession(true);
		} else {
			logger.trace("Yup, we're guchi");
			session.invalidate();
			session = req.getSession(true);
			
		}
		return null;
	}
	

}
