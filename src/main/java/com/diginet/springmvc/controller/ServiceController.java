package com.diginet.springmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.digicon.main.Services;

import com.digicon.util.MsgTypeEnum;

@RequestMapping("/service") 
@SessionAttributes({ "roles", "login" })
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
@RestController
public class ServiceController extends AbstractController{
	
	@RequestMapping(value = "/stop/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> stopService(@PathVariable String name, HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
		
		configureHttpResponse(httpServletResponse);
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		
		try {
			String str = Services.stopService(name.trim());
			if (str.startsWith("WRN")) {
				return getMessage("Não foi possível parar o serviço. Por favor finalize primeiro os serviços das instâncias.", MsgTypeEnum.MSG_ERR);
			}
		} catch (Exception e) {
			return getMessage("Não foi possível parar o serviço.", MsgTypeEnum.MSG_ERR);
		}
		
		return getMessage("Serviço parado com êxito!", MsgTypeEnum.MSG_INFO);
	}

	@RequestMapping(value = "/start/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> startService(@PathVariable String name, HttpSession session, HttpServletResponse httpServletResponse) throws IOException {		
		
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}

		configureHttpResponse(httpServletResponse);
		try {
			if (checkLicenseToContinue()) {
				Services.startService(name.trim());
			} else {
				Services.stopService(name.trim());
				return getMessage("Licença inválida.", MsgTypeEnum.MSG_ERR);
			}

		} catch (Exception e) {
			return getMessage("Não foi possível iniciar o serviço.", MsgTypeEnum.MSG_ERR);
		}
		
		return getMessage("Serviço inicializado com êxito!", MsgTypeEnum.MSG_INFO);
	}

	@RequestMapping(value = "/restart/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> restartService(@PathVariable String name, HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
		
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		try {
			if (checkLicenseToContinue()) {
				Services.restartService(name.trim());
			} else {
				Services.stopService(name.trim());
				return getMessage("Licença inválida.", MsgTypeEnum.MSG_ERR);
			}
		} catch (Exception e) {
			return getMessage("Não foi possível reiniciar o serviço.", MsgTypeEnum.MSG_ERR);
		}

		return getMessage("Serviço reiniciado com êxito!", MsgTypeEnum.MSG_INFO);
	}

	@RequestMapping(value = "/get/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getServiceStatus(HttpSession session, @RequestParam(value="name", required=false) String name, HttpServletResponse httpServletResponse) throws IOException {
		 
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		JSONArray servicesStatus = new JSONArray();    
		try {  
			servicesStatus = Services.getStatus(name); 
		} catch (Exception e) {
			e.printStackTrace(); 
		}   
		return new ResponseEntity<String>(servicesStatus.toString(), HttpStatus.OK);
	}
}
