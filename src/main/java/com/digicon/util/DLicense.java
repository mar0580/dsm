package com.digicon.util;

import br.com.digicon.code.Decode;
import br.com.digicon.code.Encode;

/** Simple example of native library declaration and usage. */
public class DLicense implements IDLicense {
	
	private String json = null;	
		
	public DLicense() throws DLicenseException {
		try {			
			Decode decode = new Decode();
			json = decode.getGenerateDigiconLicenseKeyToClient().append(json).toString();			
		}
		catch (Exception e) { 
			throw new DLicenseException(e);
		}		
	}

	/**
	 * 
	 * @param requestInstanceType DIGINET_WEBSERVICE, DIGINET OR DGAS
	 * @param requestLicenseType NEW REQUEST CLIENT TO DIGICON
	 * @return key for Digicon
	 */
	public static String generateRequestKey(String requestInstanceType, String requestLicenseType) {		
		try {			
			Encode encode = new Encode(requestInstanceType, requestLicenseType);
			requestInstanceType = encode.getGenerateClientLicenseKeyForDigicon().toString();			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return requestInstanceType;
	}
	
	@Override
	public String getLicenseKeyJsonData() {
		return json;
	}	

	@Override
	public String getUpdateKeyJsonData() {
		return json;
	}

	@Override
	public String getUpdateKeyPemFmt() {
		return json;
	}

	@Override
	public int setDSMPassword(String password) {
		return 0;
	}

	@Override
	public String getDSMPassword() {
		return "digicon";
	}

	@Override
	public int ValidateLicenseKeyPemFmt(String pusEncLicenseKeyPemFmt) {
		return 0;
	}

	@Override
	public String getLicenseKeyPemFmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createLicenseKeyFromPemFmt(String pem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createUpdateKey(String joUpdateKey) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String createNewUpdateKey() {
		// TODO Auto-generated method stub
		return null;
	}

}