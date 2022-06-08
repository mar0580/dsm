package com.digicon.util;

public interface IDLicense {

	// ==========
	// LICENSE KEY
	// ==========
	
	/**
	 * Gets the license in JSON format
	 * @return json format of license key
	 */
	public String getLicenseKeyJsonData();

	/**
	 * Gets the license in JSON format
	 * @return pem format of License Key
	 */
	public String getLicenseKeyPemFmt();

	
	/**
	 * Create the license.key from PEM
	 * @param String of PEM
	 * @return
	 */
	public int setDSMPassword(String password);

	/**
	 * Create the license.key from PEM
	 * @param String of PEM
	 * @return
	 */
	public String getDSMPassword();
	
	/**
	 * Create the license.key from PEM
	 * @param String of PEM
	 * @return
	 */
	public String createLicenseKeyFromPemFmt(String pem);
	
	/**
	 * 
	 * @param pusEncLicenseKeyPemFmt
	 * @return
	 */
	public int ValidateLicenseKeyPemFmt(String pusEncLicenseKeyPemFmt);

	// ==========
	// UPDATE KEY
	// ==========
	/**
	 * Get Update key in JSON format
	 * @return
	 */
	public String getUpdateKeyJsonData();

	/**
	 * Get Update key in PEM format
	 * @return
	 */
	public String getUpdateKeyPemFmt();

	
	/**
	 * Overrides the Update.key file by updating or removing DSM user requests
	 * @param Object to be read
	 * @return
	 */
	public int createUpdateKey(String joUpdateKey);

	/**
	 * Create new Update.key, this function is only called when the Update.key file does not yet exist in the DIGICON installation folder.
	 * @return
	 */
	public String createNewUpdateKey();
	
}