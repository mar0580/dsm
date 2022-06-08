package com.diginet.springmvc.dao;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.diginet.springmvc.entity.ModuleVO;

public interface ModuleDao {
	
	void installModule(String installModuleSQLPath) throws FileNotFoundException, IOException;
	
	void uninstallModule(String uninstallModuleSQLPath) throws FileNotFoundException, IOException;
	
	ModuleVO findModuleById(Long id );

}
