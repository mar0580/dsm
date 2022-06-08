package com.diginet.springmvc.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.digicon.helper.GenericFunctionHelper; 

import com.digicon.util.InstanceTypesEnum;
import com.digicon.util.MsgTypeEnum;
import com.diginet.springmvc.service.LicenseHelper;
import com.diginet.springmvc.service.UserService;

@RestController
@RequestMapping("/module")
@SessionAttributes({ "roles", "login" })
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
public class ModuleController extends AbstractController{
	 
	@Autowired
	UserService userService;

	@Autowired
	LicenseHelper licenseService2; 
	
	@RequestMapping(value = "/install/{product}/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> installModule(@PathVariable String product, @PathVariable String name, HttpSession session, HttpServletResponse httpServletResponse) throws IOException {
		
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		StringBuilder path = new StringBuilder("");
		if (licenseService2.isModuleInstalled(name)) {
			return getMessage("O módulo já está instalado.", MsgTypeEnum.MSG_WRN);
		}

		path.append(GenericFunctionHelper.getRootPath() + "\\scripts\\install_"
				+ InstanceTypesEnum.valueOf(product.toUpperCase()).getStrStatus() + "_" + name + ".sql");
		
		try { 
			userService.installModule(path.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return getMessage("Arquivo de instalação não encontrado", MsgTypeEnum.MSG_ERR);
			

		} catch (IOException e) {
			e.printStackTrace();
			return getMessage("Falha ao carregar o arquivo de instalação.", MsgTypeEnum.MSG_INFO);
		}

		return getMessage("Módulo instalado com êxito.", MsgTypeEnum.MSG_INFO);
	}
	 
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> getModules(HttpSession session, HttpServletResponse httpServletResponse) throws IOException, URISyntaxException {
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		licenseService.reload();
		return new ResponseEntity<String>(new JSONArray(licenseService2.getModules()).toString(), HttpStatus.OK);
	}
	
}
