package com.diginet.springmvc.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
@RequestMapping("/error")
@SessionAttributes({ "roles", "login" })
public class ErrorController {
	@RequestMapping(value = "/license", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getModal(ModelMap model, @RequestParam(value = "type", required = true) String type) {
		switch (type) {
		case "not-found":
			return "license_not_found";
		}
		return "license_not_found";
	}
}
