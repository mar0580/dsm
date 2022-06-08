package com.diginet.springmvc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digicon.util.DLicenseException;
import com.digicon.util.FileHelper;
import com.digicon.util.MsgTypeEnum;
import com.digicon.util.Util;
import com.diginet.springmvc.dto.LicenseDTO;
import com.diginet.springmvc.entity.InstanceVO;
import com.diginet.springmvc.model.Instance;

@RestController
@SessionAttributes({ "roles", "login" })
@RequestMapping("/license")
@CrossOrigin(origins = "localhost:8080", maxAge = 3600)
public class LicenseController extends AbstractController {

	private final String LICENSE_NAME = "License.key";

	/**
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @param session
	 * @param httpServletResponse
	 * @return
	 * @throws IOException
	 * @throws DLicenseException
	 * @throws ParseException
	 * @throws JSONException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateLicense(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, HttpSession session, HttpServletResponse httpServletResponse)
			throws IOException, JSONException, ParseException, DLicenseException {

		if (!confirmLogin(session, httpServletResponse)) {
			return null;
		}

		configureHttpResponse(httpServletResponse);
		if (!file.getOriginalFilename().equals(LICENSE_NAME)) {
			return getMessage("Arquivo de linceça incorreto!", MsgTypeEnum.MSG_ERR);
		}
		String FileDir = Util.getLicensePath(Util.getPath());
		try {
			InputStream is = file.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer);

			FileHelper.createKeyTemporyFile(FileDir);

			Process process;
			Runtime runtime = Runtime.getRuntime();

			for (InstanceVO instances : licenseService.getInstances()) {
				if (instances.getInstanceStatus() == 1) {
					process = Runtime.getRuntime().exec("sc query " + instances.getInstanceName().toString());
					Scanner reader = new Scanner(process.getInputStream(), "UTF-8");
					while (reader.hasNextLine()) {
						if (reader.nextLine().contains("RUNNING")) {
							process = runtime.exec("net " + "stop " + instances.getInstanceName().toString());
						}
					}
				}
			}

			FileHelper.saveUploadFile(writer.toString(), FileDir);

			Util.populateRows(new JSONObject(licenseService.getLicenseContent()));

			return getMessage("Licença atualizada com êxito.", MsgTypeEnum.MSG_INFO);
		} catch (Exception e) {

			FileHelper.renameKeyFile(FileDir);

			Util.populateRows(new JSONObject(licenseService.getLicenseContent()));

			e.printStackTrace();
			return getMessage("Não foi possível atualizar o arquivo.", MsgTypeEnum.MSG_ERR);
		}
	}

	/**
	 * 
	 * @param updateInstance
	 * @param session
	 * @param httpServletResponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/instance/original", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getOrinalLicenseInstance(@RequestBody Instance updateInstance, HttpSession session,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!confirmLogin(session, httpServletResponse)) {
			return null;
		}

		try {
			Instance originalinstance = licenseService.getOriginalLicenseInstance(updateInstance);
			return new ResponseEntity<String>(gson.toJson(originalinstance), HttpStatus.OK);
		} catch (DLicenseException e) {
			return getMessage("Licença Inválida!", MsgTypeEnum.MSG_ERR);
		}

	}

	/**
	 * 
	 * @param session
	 * @param httpServletResponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUpdateKeyJsonFmt(HttpSession session, HttpServletResponse httpServletResponse)
			throws IOException {

		if (!confirmLogin(session, httpServletResponse)) {
			return null;
		}

		reloadPemFiles();
		LicenseDTO licenseDTO = new LicenseDTO();
		licenseDTO.setInstances(licenseService.getLicenseInstancesOnlyWithUpdates());

		return new ResponseEntity<String>(gson.toJson(licenseDTO), HttpStatus.OK);
	}

	/**
	 * 
	 * @param session
	 * @param httpServletResponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get/pem-update", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getUpdateKeyPemFmt(HttpSession session, HttpServletResponse httpServletResponse)	throws IOException {		

		if (!confirmLogin(session, httpServletResponse)) {
			return null;
		}

		try {		
			return new ResponseEntity<String>(licenseService.getTypeLicenseForNewRequest(), HttpStatus.OK);
		} catch (Exception e) {
			return getMessage("Erro ao gerar request!", MsgTypeEnum.MSG_ERR);
		}
	}
}
