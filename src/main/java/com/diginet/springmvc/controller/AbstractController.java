package com.diginet.springmvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.digicon.util.LocalDateTimeSerializer;
import com.digicon.util.MsgTypeEnum;
import com.digicon.util.PemStatusEnum;
import com.digicon.util.Util;
import com.diginet.springmvc.service.LicenseHelper;
import com.diginet.springmvc.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AbstractController {

	@Autowired
	UserService userService;

	@Autowired
	LicenseHelper licenseService;

	protected Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();

	protected boolean isLoggedIn(HttpSession session) {		
		return (session.getAttribute("username") == null) ? false : true;
	}

	protected boolean confirmLogin(HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
		if (!isLoggedIn(session)) {
			System.err.println("Not logged!");
			httpServletResponse.sendRedirect("/dsm/");
			return false;
		}
		return true;
	}
	
	protected void reloadPemFiles() {
		licenseService.reload();
	}

	protected void checkLicenseToContinue(HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
		licenseService.reload();
		if (licenseService.getLicenseStatus() != PemStatusEnum.VALID) {	
			httpServletResponse.sendRedirect("/dsm/error/license?type=not-found"); 
		}	 		
		
	}
	
	protected boolean checkLicenseToContinue() throws IOException {
		if (!licenseService.isHddSerialAndMacAddress()) { 
			return false;
		}	
		
		return true;		
	}
	
	protected void configureHttpResponse(HttpServletResponse httpServletResponse){
		httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
		httpServletResponse.setDateHeader("Expires", 0); // Proxies.
	}

	protected ResponseEntity<String> getMessage(String msg, MsgTypeEnum msgType) throws UnsupportedEncodingException {
		return new ResponseEntity<String>(Util.msg(msg, msgType), HttpStatus.OK);
	}
}