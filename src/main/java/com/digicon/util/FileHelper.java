package com.digicon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
 
public final class FileHelper {
	
	private final static String LICENSE_NAME = "License.key";
	 
	public static String readFile(String pathReturned) throws IOException {
    	String filePath = pathReturned; 
    	String returnValue = "";
    	Scanner in = null;
    	try {  
    		in = new Scanner(new FileReader(filePath));
			while (in.hasNext()){  
				returnValue += in.next(); 
			}
			System.err.println(returnValue); 
    	} catch (FileNotFoundException e) {
			e.printStackTrace();  
		} finally {
			in.close();
		}
    	
    	return returnValue;	
    }
	
	public static void saveUploadFile(String input, String FileDir) throws IOException {		
		Files.write(Paths.get(FileDir), input.getBytes());
	}
	
	public static void createKeyTemporyFile(String file) throws IOException {
	    File StartingFile = new File(file);
	    File EndingFile = new File(StartingFile.getAbsolutePath() + ".tmp");

	    BufferedReader br = new BufferedReader(new FileReader(file));
	    PrintWriter pw = new PrintWriter(new FileWriter(EndingFile));

	    String line;
	    while ((line = br.readLine()) != null) {
	        pw.write(line);
	    }

	    pw.close();
	    br.close();
	}
	
	public static void renameKeyFile(String pathLicenseKey) {
		try {
			Path source = Paths.get(pathLicenseKey + ".tmp");
			Files.move(source, source.resolveSibling(LICENSE_NAME), StandardCopyOption.REPLACE_EXISTING);	
			deleteFile(pathLicenseKey);
		} catch (IOException ignored) {
		}
	}

	public static void deleteFile(String pathLicenseKey) {
		try {
			Files.delete(Paths.get(pathLicenseKey + ".tmp"));
		} catch (IOException ignored) {
		}
	}
}
