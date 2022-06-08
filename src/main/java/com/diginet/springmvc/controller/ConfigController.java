package com.diginet.springmvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.digicon.util.MsgTypeEnum;
import com.digicon.util.Util;
import com.google.gson.Gson;

import br.com.digicon.dto.PortsDTO;
import br.com.digicon.exception.PortAlreadyInUseException;
import br.com.digicon.portconfig.PortConfiguration;

@RestController
@RequestMapping("/config")
@SessionAttributes({ "roles", "login" })
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
public class ConfigController extends AbstractController {

	@RequestMapping(value = "/ports", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getPorts(HttpSession session, HttpServletResponse httpServletResponse)
			throws Exception {

		if (!confirmLogin(session, httpServletResponse)) {
			return null;
		}
		configureHttpResponse(httpServletResponse);
		try {
			PortsDTO portsDTO = PortConfiguration.getPorts();
			return new ResponseEntity<String>(new Gson().toJson(portsDTO).toString(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(Util.msg("Não foi possível recuperar as portas", MsgTypeEnum.MSG_ERR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/ports", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updatePorts(@RequestBody PortsDTO portsDTO, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!confirmLogin(session, httpServletResponse)) {
			return null;
		}
		try {
			PortConfiguration.setPorts(portsDTO);
			return getMessage("Porta(s) atualizada(s) com êxito!", MsgTypeEnum.MSG_INFO);
		} catch (PortAlreadyInUseException e) {
			return new ResponseEntity<String>(Util.msg(e.getMessage(), MsgTypeEnum.MSG_ERR), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(Util.msg("Não foi possível realizar esta operação", MsgTypeEnum.MSG_ERR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
