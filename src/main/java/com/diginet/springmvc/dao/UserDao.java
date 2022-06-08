package com.diginet.springmvc.dao;

import java.util.List;

import com.diginet.springmvc.entity.DeviceVO;
import com.diginet.springmvc.entity.User;

 
public interface UserDao {
	 
	void save(User user);
	
	void deleteBySSO(String sso); 
	 
	List<DeviceVO> findAllUsers();

	List<Object> find();

}

