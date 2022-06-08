package com.diginet.springmvc.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.diginet.springmvc.entity.DeviceVO;
import com.diginet.springmvc.entity.InstanceVO;
import com.diginet.springmvc.entity.ModuleVO;
import com.diginet.springmvc.entity.User; 


public interface UserService {
	
	void saveUser(User user); 
 
	void deleteUserBySSO(String sso); 
	
	List<DeviceVO> findAllUsers();  
	
	boolean isUserSSOUnique(Integer id, String sso);   
 
	List<Object> find();
	
	void updateLicenceStatusByHash(String hash, int Status); 

	InstanceVO findInstanceByName(String name);
	
	List<InstanceVO> findAllInstances();
	
	List<InstanceVO> findInstancesByType(int typeInstance);	
	
	void updateInstance(InstanceVO input);
	
	void updateInstanceSetOutdated(String name);
	
	void installModule(String path) throws FileNotFoundException, IOException ;
	
	void uninstallModule(String path) throws FileNotFoundException, IOException ;
	
	ModuleVO findModuleById(Long id);

	
}