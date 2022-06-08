package com.diginet.springmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.digicon.util.MsgTypeEnum;
import com.diginet.springmvc.service.LogService;

@RestController
@RequestMapping("/log")
@SessionAttributes({ "roles", "login" })
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
public class LogController extends AbstractController{

	@Autowired
	LogService logService;
	
	private final String DELETE = "delete"; 
	private final String SEND   = "send";
	private final String GET    = "get"; 
	
	@RequestMapping(value = "/{operation}/{name}", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> factorOperation(@PathVariable String name, @PathVariable String operation, HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
		
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);		
		switch(operation){
		case DELETE:
			return logService.deleteLog(name);
		case SEND:
			return logService.sendLog(name); 
		case GET:
			System.out.println("Instance name to get log: "+name);
			return logService.getLog(name);
		default:
			return getMessage("Operação inválida", MsgTypeEnum.MSG_ERR);
		}
		
	}
}
