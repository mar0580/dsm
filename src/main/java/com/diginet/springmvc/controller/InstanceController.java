package com.diginet.springmvc.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.digicon.util.StatusUpdaterUtil;
import com.digicon.util.Util;
import com.diginet.springmvc.entity.InstanceVO;
import com.diginet.springmvc.model.Instance;
import com.diginet.springmvc.model.LicenseBuilder;
import com.diginet.springmvc.service.InstanceService;
import com.diginet.springmvc.service.UserService;

import br.com.digicon.helper.InstanceStatusValueEnum;
//import br.com.digicon.main.Instance;
import br.com.digicon.main.InstanceDeleter;
import br.com.digicon.main.InstanceInstaller;



@RequestMapping("/instance")
@RestController
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
@SessionAttributes({ "roles", "login" })
public class InstanceController extends AbstractController {

	@Autowired
	UserService userService;

	@Autowired
	InstanceService instanceService;
	
	@RequestMapping(value = "/install/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> installInstance(@PathVariable String type, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		String nextInstanceName = Util.lastInstanceToInstall(type);

		try {

			Instance instance = new LicenseBuilder(). findInstanceByName(nextInstanceName)
					.orElseThrow(() -> new Exception("Instancia nao encontrada com nome " + nextInstanceName));

			InstanceStatusValueEnum nextInstanceStatus = new StatusUpdaterUtil(instance.getEndDate()).updateStatus(instance);
			
			if (nextInstanceStatus == InstanceStatusValueEnum.EXPIRED) {

				Process process;
				Runtime runtime = Runtime.getRuntime();
				Scanner reader = null;
				for (InstanceVO instances : licenseService.getInstances()) {
					userService.updateInstanceSetOutdated(instances.getInstanceName().toString());
					if (instances.getInstanceStatus() == 3) {
						process = Runtime.getRuntime().exec("sc query " + instances.getInstanceName().toString());
						reader = new Scanner(process.getInputStream(), "UTF-8");
						while (reader.hasNextLine()) {
							if (reader.nextLine().contains("RUNNING")) {
								process = runtime.exec("net " + "stop " + instances.getInstanceName().toString());
							}
						}
					}
				}
				
				return getMessage("Não foi possivel executar o processo de instalação - Instância expirada.", MsgTypeEnum.MSG_ERR);
			} else {
				String str = InstanceInstaller.install(nextInstanceName, nextInstanceStatus);
				if (str.startsWith("error")) {
					return getMessage(str, MsgTypeEnum.MSG_ERR);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return getMessage("Não foi possivel executar o processo de instalação", MsgTypeEnum.MSG_ERR);
		}
		
		return getMessage("Instalação realizada com sucesso!", MsgTypeEnum.MSG_INFO);
	}

	@RequestMapping(value = "/uninstall/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uninstallInstance(@PathVariable String name, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException {
		configureHttpResponse(httpServletResponse);
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		try {

			String str = InstanceDeleter.deleteInstance(name, InstanceStatusValueEnum.NOT_INSTALLED.getValue());
			if (str.startsWith("error")) {
				return getMessage(str, MsgTypeEnum.MSG_ERR);
			}

		} catch (Exception e1) {
			return getMessage("Não foi possivel executar o processo de desinstalação", MsgTypeEnum.MSG_ERR);
		}

		return getMessage("Processo realizado com sucesso!", MsgTypeEnum.MSG_INFO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateInstance(@RequestBody InstanceVO input, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		try {

			userService.updateInstance(input);
			return getMessage("Os dados foram alterados com sucesso! A instância está sendo reiniciada.",
					MsgTypeEnum.MSG_INFO);

		} catch (Exception e) {
			e.printStackTrace();
			return getMessage("Não foi possível realizar esta operação", MsgTypeEnum.MSG_ERR);
		}
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> factorInstanceMethod(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "name", required = false) String name, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException, URISyntaxException {

		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		reloadPemFiles();

		if (type == null && name == null) {

			return instanceService.getAllInstances();

		} else if (type != null && name == null) {

			JSONArray jaInstances = new JSONArray(gson.toJson(licenseService.getLicenseInstancesWithUpdates(type)));
			return new ResponseEntity<String>(jaInstances.toString(), HttpStatus.OK);

		} else if (name != null && type == null) {

			return instanceService.getInstancesByName(name);

		} else {
			return getMessage("Operação não suportada.", MsgTypeEnum.MSG_ERR);
		}
	}

	@RequestMapping(value = "/get/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> factorInstanceMethod(HttpSession session, HttpServletResponse httpServletResponse)
			throws IOException, URISyntaxException {
		
		if (!confirmLogin(session, httpServletResponse)){
			return null;
		}
		configureHttpResponse(httpServletResponse);
		reloadPemFiles();
		return getMessage(new JSONArray(licenseService.getInstanceTypes()).toString(), MsgTypeEnum.MSG_INFO);
	}
}
