package com.diginet.springmvc.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.digicon.util.MsgTypeEnum;
import com.diginet.springmvc.model.Instance;
import com.diginet.springmvc.service.LicenseHelper;

@RestController
@SessionAttributes({ "roles", "login" })
@RequestMapping("/request")
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
public class RequestController extends AbstractController {

	@Autowired
	LicenseHelper licenseService;

	@RequestMapping(value = "/module", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> requestModule(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "type", required = true) String type, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException, URISyntaxException {
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		
		return getMessage(
				"Solicitações de mudança de licença devem ser encaminhadas para suporte.vca@digicon.com.br.",
				MsgTypeEnum.MSG_WRN);
	}

	@RequestMapping(value = "/module/cancel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cancelModule(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "cancel", required = true) Boolean cancel, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException, URISyntaxException {

		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		
		return getMessage(
				"Solicitações de mudança de licença devem ser encaminhadas para suporte.vca@digicon.com.br.",
				MsgTypeEnum.MSG_WRN);
	}

	@RequestMapping(value = "/instances/{type}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> requestInstances(@PathVariable String type, @RequestBody List<Instance> instances,
			HttpSession session, HttpServletResponse httpServletResponse) throws IOException, URISyntaxException {

		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		return getMessage(
				"Solicitações de mudança de licença devem ser encaminhadas para suporte.vca@digicon.com.br.",
				MsgTypeEnum.MSG_WRN);
	}
}
