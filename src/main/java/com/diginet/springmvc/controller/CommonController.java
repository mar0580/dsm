package com.diginet.springmvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.digicon.util.DLicenseException;
import com.digicon.util.LocalDateTimeSerializer;
import com.digicon.util.MsgTypeEnum;
import com.digicon.util.PathHelper;
import com.digicon.util.Util;
import com.diginet.springmvc.entity.LoginVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import br.com.digicon.dao.InstanceDAO;
import br.com.digicon.helper.InstanceStatusValueEnum;

@Controller
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
@RequestMapping("/")
@SessionAttributes({ "roles", "login" })
public class CommonController extends AbstractController {

	@Autowired
	MessageSource messageSource;

	private JSONArray jaAllInstances = new JSONArray();

	/*********************************************
	 * DSM dev by CTS/ICTS Group
	 * 
	 * Jose Oliveira | Nicolas Meinen | Marcos Alex | Diego Kubota
	 * 
	 * 
	 * v0.0 (Still being developed)
	 * 
	 * DEFINITIONS:
	 * 
	 * @PathParameter is a parameters contained in the URL
	 * @BodyParameter is the payload from the HTTP message
	 * 
	 *********************************************/

	/***
	 * Index page (localhost:8081/)
	 ***/

	@RequestMapping(value = { "/", "/list, /login" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model, HttpSession session, HttpServletResponse httpServletResponse) throws Exception {		
		configureHttpResponse(httpServletResponse);
		checkLicenseToContinue(session, httpServletResponse);
		if (!isLoggedIn(session)) {
			System.err.println("Not logged!");
			return "login";
		}
		return "index";
	}

	@RequestMapping(value = "/modal/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getModal(ModelMap model, @PathVariable String name, HttpSession session,
			HttpServletResponse httpServletResponse) {
		
		configureHttpResponse(httpServletResponse);
		if (!isLoggedIn(session)) {
			System.err.println("Not logged!");
			return "login";
		}
		switch (name) {
		case "send-update-key":
			return "modal_send_update_key";
		case "config-instance":
			return "modal_instance_config";
		case "alter-login":
			return "modal_alter_login";
		case "update-license":
			return "modal_update_license";
		case "change-diginet-port":
			return "modal_change_diginet_port";
		case "request-instance":
			return "modal_request_instance";
		case "wait-install":
			return "modal_wait_install";
		case "request-module":
			return "modal_wait_install";
		case "cancel-module":
			return "modal_wait_install";
		case "wait-service":
			return "modal_wait_service";
		case "show-log":
			return "modal_show_log";
		case "wait-log":
			return "modal_wait_log";
		case "install-module":
			return "modal_wait_install";
		default:
			return "test";
		}
	}

	@RequestMapping(value = "/execlogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> rememberThought(@RequestBody LoginVo input, HttpSession session)
			throws UnsupportedEncodingException {
		
		if (licenseService.getCompany().getUsername().equals(input.getUsername()) && licenseService.getCompany().getPassword().equals(input.getPassword())) {
			System.out.println(isLoggedIn(session));			
			session.setAttribute("username", input.getUsername());
			return new ResponseEntity<String>(Util.msg("true", MsgTypeEnum.MSG_INFO), HttpStatus.OK);
		}

		return new ResponseEntity<String>(Util.msgBase64("Login/Senha incorretos.", MsgTypeEnum.MSG_ERR),
				HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public ResponseEntity<String> logout(HttpSession session) {
		
		session.removeAttribute("username");
		return new ResponseEntity<String>("true", HttpStatus.OK);
	}

	@RequestMapping(value = { "/init" }, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> init(HttpSession session, HttpServletResponse httpServletResponse)
			throws IOException, URISyntaxException, JSONException, ParseException {
		
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		JSONObject joLicense = new JSONObject();
		licenseService.reload();
		
		String licenseContent = null;
		try {
			licenseContent = licenseService.getLicenseContent();
		} catch (DLicenseException e) {
			return getMessage("Licença inválida!", MsgTypeEnum.MSG_ERR);
		}

		if (!licenseService.isValid()) {
			return new ResponseEntity<String>(joLicense.toString(), HttpStatus.OK);
		}

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).create();
		joLicense.put("company", new JSONObject(licenseService.getCompany()));
		joLicense.put("machine", new JSONObject(licenseService.getMachine()));
		joLicense.put("modules", new JSONArray(licenseService.getModules()));

		if (InstanceDAO.getInstancesCount() == null) {
			joLicense.put("hasConnection", "false");
			return new ResponseEntity<String>(joLicense.toString(), HttpStatus.OK);
		} else if (InstanceDAO.getInstancesCount() == 0) {
			Util.populateRows(new JSONObject(licenseContent));
		}

		joLicense.put("hasConnection", "true");
		joLicense.put("instance_types", new JSONArray(licenseService.getInstanceTypes()));
		jaAllInstances = new JSONArray(gson.toJson(licenseService.getInstances()));
		joLicense.put("instances", jaAllInstances);		
		return new ResponseEntity<String>(joLicense.toString(), HttpStatus.OK);
	}

	@RequestMapping(value = "/alter/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> updateLogin(@RequestBody String input, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException {

		configureHttpResponse(httpServletResponse);
		String msg = new String();
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}

		msg = licenseService.updatePass(input);

		licenseService.reload();
		
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	/*
	 * TODO: Put this into instanceController. The thing is: i did not put it
	 * yet 'cause it will break the diginet dfs server.
	 */
	@RequestMapping(value = "/controll/instance/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> enableInstanceToRun(@PathVariable String name, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException {
		/*
		 * NEVER ASK FOR LOGIN IN THIS METHOD
		 */
		configureHttpResponse(httpServletResponse);
		int instanceStatus;
		String msg = new String();

		try {
			instanceStatus = userService.findInstanceByName(name).getInstanceStatus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = Util.msg("unabled", MsgTypeEnum.MSG_INFO);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}

		if (instanceStatus == InstanceStatusValueEnum.EXPIRED.getValue()) {
			msg = Util.msg("unabled", MsgTypeEnum.MSG_INFO);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}  
		msg = Util.msg("abled", MsgTypeEnum.MSG_INFO);
		return new ResponseEntity<String>(msg, HttpStatus.OK); 
	}
	  
	@RequestMapping(value = "/controll/port", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getAllowedPortsForClientSocket() throws IOException{
		String jsonFile = Files.lines(Paths.get(PathHelper.getPortConfigPath())).collect(Collectors.joining());
		JsonObject json = gson.fromJson(jsonFile, JsonObject.class);   
		json.remove("DBPort");   
		return new ResponseEntity<String>(gson.toJson(json), HttpStatus.OK);  
	}
	
}