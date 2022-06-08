package com.digicon.dsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator {
		private String protocol;
		private String address;
		private String licenseData;
		private String pathHTTPLicenses;
		private URL url;
		
		public Validator() { 
			this.protocol ="http"; 
			this.address ="suporte-vca.digicon.com.br";
			this.pathHTTPLicenses = "/forum/download/series/";
			this.licenseData = "";
		}
		
		/**
		 * Class to check if the database is the most current
		 * Access HTTP and reads the file, the version number + ";" + Path database
		 * @return License data
		 * @throws IOException
		 */
		public String loadsLicenseData(int serie) throws Exception{

			url = new URL(protocol,address,pathHTTPLicenses + serie + ".dsm");
//			url = new URL("http://localhost/testDSM/file.dsm");
				
			URLConnection urlConn =  url.openConnection();
			urlConn.connect();
			
			InputStream inputStream = urlConn.getInputStream();
			licenseData = convertStreamToString(inputStream);  
			
			return licenseData;
		}
		
		/**
		 * Converts an InputStream and returns a String
		 * @param is
		 * @return String
		 * @throws IOException
		 */
		private String convertStreamToString(InputStream is) throws IOException {  
			  
	        if (is != null) {  
	            StringBuilder sb = new StringBuilder();  
	            String line;  
	            try {  
	                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
	                while ((line = reader.readLine()) != null) {  
	                    sb.append(line).append("\n");  
	                }  
	            } finally {  
	                is.close();  
	            }  
	            return sb.toString();  
	        } else {          
	            return "";  
	        }  
	    }  
		
		/**
		 * Converts and returns the date
		 * @param value
		 * @return Date formatted for yyyyMMdd
		 */
		public Date stringToDate(String value) {
		       
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
	        f.setLenient(false);
	        Date date = null;
	        
	        try {
	            date = f.parse(value);
	        } catch (ParseException e) {
	        	System.out.println("[ERROR] " + e);
	        }
	        return date;
	    }
		
		public boolean compareExpiration(int daysEnabled, int daysReturned){
			if (daysEnabled >= daysReturned){
				return true;
			}
			return false;   
		} 
}
