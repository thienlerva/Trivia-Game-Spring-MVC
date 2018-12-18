package com.ex.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class NewUserServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(NewUserServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
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
		
		//Set ok status code
		resp.setStatus(HttpServletResponse.SC_OK);

	}
	
}