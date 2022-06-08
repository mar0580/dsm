package com.digicon.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.digicon.helper.GenericFunctionHelper;

import com.diginet.springmvc.entity.ModuleVO;
import com.diginet.springmvc.service.UserService;

@Component
public class ModuleHelper {
	
	@Autowired
	UserService  userService;
	
	public void uninstallModule(String product, String name ){
		String path = "";
		
		System.out.println("root path    [" + GenericFunctionHelper.getRootPath() + "]");
		
		path += GenericFunctionHelper.getRootPath() + "\\scripts\\uninstall_" + product + "_" + name + ".sql";
		System.out.println("path[" + path + "]");

		try {
			userService.uninstallModule(path); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isModuleInstalled(String name ) {
		System.out.println("Name ["+name+"]");
		
		boolean isInstaled = true;
		ModulesEnum modules = ModulesEnum.valueOf(name.toUpperCase());
		for (Long id : modules.getIds()) {
			System.out.println("Module ID ["+id+"]");
			try {
				ModuleVO module = userService.findModuleById(id);
				if (module == null) {
					isInstaled = false;
				}
				System.out.println("Module ["+module.getNome()+"]");
			} catch (Exception e) {
				e.printStackTrace();
				isInstaled = false;
			}
		}
		System.out.println("isInstaled ["+isInstaled+"]");
		return isInstaled;
	}
}
