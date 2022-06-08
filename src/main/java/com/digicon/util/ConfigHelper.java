package com.digicon.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class ConfigHelper {
	
	public static String getDbPortFromConfigFile(){
		try{
			String path = PathHelper.getPortConfigPath(); 
			String fullConfigFile = Files.readAllLines(Paths.get(path)).stream().collect(Collectors.joining());
			JSONObject json = new JSONObject(fullConfigFile);
			return json.get("DBPort").toString(); 
		} catch (IOException e){   
			return "3306";
		}
	}
	
}
