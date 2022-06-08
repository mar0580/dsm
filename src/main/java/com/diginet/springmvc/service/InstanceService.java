package com.diginet.springmvc.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digicon.util.DateSerializer;
import com.digicon.util.InstanceTypesEnum;
import com.digicon.util.LocalDateTimeSerializer;
import com.digicon.util.MsgTypeEnum;
import com.digicon.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.digicon.helper.InstanceStatusValueEnum;

@Service("instanceService") 
public class InstanceService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	LicenseHelper licenseService2;
	
	public ResponseEntity<String> getInstancesByType(String type) throws IOException {
		JSONArray jaInstances = new JSONArray();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		jaInstances = new JSONArray(gson.toJson(licenseService2.getInstanceByType(InstanceTypesEnum.valueOf(type.toUpperCase()))));
		return new ResponseEntity<String>(jaInstances.toString(), HttpStatus.OK);
	}

	public ResponseEntity<String> getAllInstances(){
		JSONArray jaInstances = new JSONArray();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		jaInstances = new JSONArray(gson.toJson(licenseService2.getInstances()));
		return new ResponseEntity<String>(jaInstances.toString(), HttpStatus.OK);
	}

	public ResponseEntity<String> getInstancesByName(String name) throws UnsupportedEncodingException{ 
		String msg = new String();
		JSONObject instance = new JSONObject();
		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
					.registerTypeAdapter(Date.class, new DateSerializer())
					.create();
			com.diginet.springmvc.entity.InstanceVO instanceVO = userService.findInstanceByName(name);
			instanceVO.setStrInstanceStatus(InstanceStatusValueEnum.getStrStatus(instanceVO.getInstanceStatus()));
			instanceVO.setStrInstanceType(InstanceTypesEnum.getStringName(instanceVO.getInstanceType()));
			instance = new JSONObject(gson.toJson(instanceVO));
		} catch (Exception e1) {
			msg = Util.msg("Não foi possivel recuperar os dados da instância", MsgTypeEnum.MSG_ERR);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}
		return new ResponseEntity<String>(instance.toString(), HttpStatus.OK);
	}
	
}
