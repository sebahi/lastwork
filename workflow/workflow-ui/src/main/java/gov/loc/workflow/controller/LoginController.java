package gov.loc.workflow.controller;

import java.net.UnknownHostException;

import javax.management.MalformedObjectNameException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.loc.workflow.domain.Env;
import gov.loc.workflow.util.ObtainServerIPPort;

@Controller
public class LoginController {
	
	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	Env environment;
	
	@Autowired
	ObtainServerIPPort serverIPPort;
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String login() throws MalformedObjectNameException, UnknownHostException {
		environment.setWebServer(serverIPPort.getServerIPPort());
		logger.debug("The tomcat server IP and Port are: "+serverIPPort.getServerIPPort());
		return "login";
	}
	
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(Model model) {
		
		model.addAttribute("error", "true");
		return "login";
	}
	
	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	    	logger.debug("Auth is: "+auth);
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		return "login";
	}
}
