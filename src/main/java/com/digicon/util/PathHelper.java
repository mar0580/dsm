package com.digicon.util;

public class PathHelper {
	
	/**
	 * Esse parametro indica quantas vezes deve ser cortado o path.
	 * Como esta na pasta root/Tomcat, tem que voltar 1 vez para ir pro root (Diginet)
	 * e 2 para ir pro DigiconApp.
	 */
	private static final int GO_BACK_TIMES = 2; 
	
	/** 
	 * Esse parametro indica como ir do ROOT para a licença.
	 */
	private static final String GO_TO_LICENSE = "\\license.json";
	
	/**
	 * Esse parâmetro, considerando-se que a presente aplicação roda em \diginet\tomcat,
	 * indica que deve retornar 1 path pra obter o caminho de \diginet, onde ficam
	 * armazenados alguns arquivos de conf.
	 */
	private static final int GO_TO_DIGINET = 1;  
	
	/**
	 * Parâmetro de ajuda para fazer o caminho até o arquivo de configuração de portas
	 */
	private static final String GO_TO_CONFIGURATION = "\\port_config.json"; 
	  
	private static String goBackPath(String path, int iterations){
		if (iterations == 0) return path;
		else return goBackPath(path.substring(0,path.lastIndexOf('\\')), --iterations);
	}  
	
	public static String getLicensePath(){
		 return getRootPath() + GO_TO_LICENSE; 
	}
	
	public static String getPortConfigPath(){
		return getDiginetPath() + GO_TO_CONFIGURATION;
	}
	
	public static String getRootPath(){
		return goBackPath(System.getProperty("user.dir"), GO_BACK_TIMES);
	}
	
	public static String getDiginetPath(){ 
		return goBackPath(System.getProperty("user.dir"), GO_TO_DIGINET);
	} 
}
