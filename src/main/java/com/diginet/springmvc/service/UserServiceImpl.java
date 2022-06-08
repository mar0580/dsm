package com.diginet.springmvc.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diginet.springmvc.dao.InstanceDao;
import com.diginet.springmvc.dao.ModuleDao;
import com.diginet.springmvc.dao.UserDao;
import com.diginet.springmvc.entity.DeviceVO;
import com.diginet.springmvc.entity.InstanceVO;
import com.diginet.springmvc.entity.ModuleVO;
import com.diginet.springmvc.entity.User;

 
@Service("userService")
@Transactional  
public class UserServiceImpl implements UserService{
 
	@Autowired
	private UserDao dao;
	
	@Autowired 
	private InstanceDao instanceDao;
	
	@Autowired 
	private ModuleDao moduleDao;
 
	
	public void saveUser(User user) {
		dao.save(user); 
	} 
 	
	 
	public void deleteUserBySSO(String sso) { 
		dao.deleteBySSO(sso);
	}

	public List<DeviceVO> findAllUsers() {
		return dao.findAllUsers();
	}

	public List<Object> find(){
		return dao.find();
	} 
	
	@Override
	public boolean isUserSSOUnique(Integer id, String sso) {
		return false;
	}

	@Override
	public List<InstanceVO> findAllInstances() {
		return instanceDao.getAllInstances();
	}

	@Override
	public void updateLicenceStatusByHash( String hash, int Status ) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<InstanceVO> findInstancesByType( int typeInstance ) {
		return instanceDao.getInstancesByType(typeInstance );
	}
 
	@Override
	public void updateInstanceSetOutdated(String name) {
		instanceDao.updateInstanceSetOutdated(name);     
	}

	@Override
	public void updateInstance( InstanceVO input ) {
		instanceDao.updateInstance(input);
	}

	@Override
	public InstanceVO findInstanceByName(String name) {
		return instanceDao.getInstanceByName(name);
	}


	@Override
	public void installModule(String path) throws FileNotFoundException, IOException {
		moduleDao.installModule(path);
	}
	
	@Override
	public void uninstallModule(String path) throws FileNotFoundException, IOException {
		moduleDao.uninstallModule(path);
	}
	
	@Override
	public ModuleVO findModuleById(Long id) {
		return moduleDao.findModuleById(id);
	}
}
