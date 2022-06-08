package com.digicon.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.diginet.springmvc.model.Instance;
import com.diginet.springmvc.model.License;
import com.diginet.springmvc.model.Module;
import com.diginet.springmvc.model.Product;

import br.com.digicon.dao.InstanceDAO;
import br.com.digicon.helper.InstanceEnum;
import br.com.digicon.model.InstanceVO;

public class Util {
	
	public static final String DEBUG_PATH = "C:\\DigiconApp";
	public static final String PATH_TO_LICENSE = "\\License.key";
	public static final String LICENSE_NAME = "License.key";
	
	public static String getPath() {
		String currPath = System.getProperty("user.dir");
		currPath = goBackPath(currPath, 2);
		return currPath;
	}
	
	public static String getLicensePath(String pathToLicense) {
		File f = new File(pathToLicense + PATH_TO_LICENSE);
		if (f.exists()) {
			System.out.println("Found the License in "+pathToLicense);
			return pathToLicense + PATH_TO_LICENSE;
		} else {
			System.out.println("Found the License in "+DEBUG_PATH);
			return DEBUG_PATH+"\\"+LICENSE_NAME;
		}
	}
	
	public static String goBackPath(String path, int iterations){
		if (iterations == 0) return path;
		else return goBackPath(path.substring(0,path.lastIndexOf('\\')), --iterations);
	}
	
	public static String populateRows(JSONObject license) throws ParseException {

		JSONObject products = license.getJSONObject("products");

		if (products.has(InstanceTypesEnum.DIGINET.getStrStatus())) {			
			JSONObject productDiginet = products.getJSONObject(InstanceTypesEnum.DIGINET.getStrStatus());
			getInstances(productDiginet, InstanceTypesEnum.DIGINET);
		}

		if (products.has(Util.addZeroIfLess10(InstanceTypesEnum.GAS.getStrStatus()))) {			
			JSONObject productGas = products.getJSONObject(InstanceTypesEnum.GAS.getStrStatus());
			getInstances(productGas, InstanceTypesEnum.GAS);
		}

		if (products.has(Util.addZeroIfLess10(InstanceTypesEnum.SAM.getStrStatus()))) {			
			JSONObject productSam = products.getJSONObject(InstanceTypesEnum.SAM.getStrStatus());
			getInstances(productSam, InstanceTypesEnum.SAM);
		}

		return null;
	}

	public static Boolean isLicenseInstancesEquals(Instance instance1, Instance instance2) {
		return (instance1.getProduct() == instance2.getProduct() && instance1.getId() == instance2.getId());
	}

	public static Boolean isThisModule(Module module, String type, String name) {
		return (module.getType().toLowerCase().trim().equals(type.toLowerCase().trim()) && module.getName().toLowerCase().trim().equals(name.toLowerCase().trim()));
	}

	public static Boolean isEndDateEqual(LocalDateTime date1, LocalDateTime date2) {
		String strUpdateEndDate = date1.toString().split("T")[0];
		String strLicenseEndDate = date2.toString().split("T")[0];
		return strUpdateEndDate.equals(strLicenseEndDate);
	}

	public static Boolean hasLicense(License license) {
		return license != null;
	}

	public static Boolean hasInstances(Product product) {
		return product.getInstances() != null;
	}

	public static Boolean hasInstances(List<Instance> instances) {
		return (instances != null) && (instances.size() > 0);
	}

	public static Boolean hasModules(Product product) {
		return product.getModules() != null;
	}

	public static boolean hasUpdates(Instance instance) {
		if (!instance.isResgistered()) {
			return true;
		} else if (instance.isResgistered() && (instance.isDeviceLimitExtended() || instance.isEndDateExtended())) {
			return true;
		}
		return false;
	}

	public static boolean hasUpdates(List<Instance> instances) {
		for (Instance instance : instances) {
			if (!instance.isResgistered()) {
				return true;
			} else if (instance.isResgistered() && (instance.isDeviceLimitExtended() || instance.isEndDateExtended())) {
				return true;
			}
		}
		return false;
	}

	public static String getInstances(JSONObject product, InstanceTypesEnum instanceType) throws ParseException {
		JSONArray instances;
		try {
			instances = product.getJSONArray("instances");
			List<InstanceVO> persistedInstances = InstanceDAO.getAllInstances();

			for (Object instance : instances) {
				InstanceVO instanceVO = new InstanceVO();
				JSONObject parametrizedInstance = ((JSONObject) instance);
				instanceVO = setDefaultParameters(instanceVO, parametrizedInstance.getInt("id"), instanceType);
				persistInstance(instanceVO, persistedInstances);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static InstanceVO setDefaultParameters(InstanceVO instanceVO, int id, InstanceTypesEnum instanceType)
			throws ParseException {

		instanceVO.setInstanceName(instanceType.toString().toLowerCase() + "-" + Util.addZeroIfLess10(id));
		instanceVO.setInstanceType(instanceType.getValue());
		instanceVO.setServerIp("0.0.0.0");
		instanceVO.setRemoteServerUrl("http://127.0.0.1:8080/diginet2/diginet-ws");

		int port = 0;
		switch (instanceType) {
		case DIGINET:
			port = 3000 + id;
			instanceVO.setSummerTimeStart(new SimpleDateFormat("yyyy-MM-dd").parse("2017-10-10"));
			instanceVO.setSummerTimeEnd(new SimpleDateFormat("yyyy-MM-dd").parse("2018-02-10"));
			break;
		case GAS:
			port = 4000 + id;
			instanceVO.setSummerTimeStart(new SimpleDateFormat("yyyy-MM-dd").parse("2017-10-10"));
			instanceVO.setSummerTimeEnd(new SimpleDateFormat("yyyy-MM-dd").parse("2018-02-10"));
			instanceVO.setRemoteServerPort(61000);
			instanceVO.setRemoteServerUser("admin-digicon");
			instanceVO.setRemoteServerUserPasswd("digicon");
			instanceVO.setRemoteServerId(300);
			instanceVO.setRemoteServerIp("127.0.0.1");
			break;
		case SAM:
			port = 5000 + id;
			break;
		}

		instanceVO.setServerPort(port);
		return instanceVO;
	}

	private static String persistInstance(InstanceVO instance, List<InstanceVO> persistedInstances) {
		if (canInsert(instance, persistedInstances)) {
			InstanceDAO.insertNewInstance(instance);
		}
		return null;
	}

	private static boolean canInsert(InstanceVO instance, List<InstanceVO> persistedInstances) {
		boolean insert = true;
		for (InstanceVO persistedInstance : persistedInstances) {
			if (persistedInstance.getInstanceName().equals(instance.getInstanceName())) {
				insert = false;
			}
		}
		return insert;
	}

	public static String lastInstanceToInstall(String type) {
		List<InstanceVO> enabledInstances = InstanceDAO.getEnabledInstances(InstanceEnum.valueOf(type));
		return enabledInstances.get(0).getInstanceName();
	}

	public static String addZeroIfLess10(String number) {
		return (Integer.parseInt(number) > 9) ? number : "0" + number;
	}

	public static String addZeroIfLess10(int number) {
		return (number > 9) ? "" + number : "0" + number;
	}
	
	public static String msg(String msg, MsgTypeEnum type) throws UnsupportedEncodingException {
		JSONObject jo;
		if (type == null) {
			jo = new JSONObject("{ info : " + msg + "}");
		} else {
			jo = new JSONObject("{" + MsgTypeEnum.getStrStatus(type) + " : " + msg + "}");
		}
		return jo.toString();
	}

	public static String msgBase64(String msg, MsgTypeEnum type) throws UnsupportedEncodingException {
		JSONObject jo;

		msg = base64Encode(msg);
		msg = msg.replaceAll("=", "DIGICONEQUAL");

		if (type == null) {
			jo = new JSONObject("{ info : " + msg + "}");
		} else {
			jo = new JSONObject("{" + MsgTypeEnum.getStrStatus(type) + " : " + msg + "}");
		}
		return jo.toString();
	}

	public static String base64Encode(String text) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(text.getBytes("UTF-8"));
	}
}
