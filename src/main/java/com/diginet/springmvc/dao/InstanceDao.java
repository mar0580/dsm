package com.diginet.springmvc.dao;

import java.util.List;

import com.diginet.springmvc.entity.InstanceVO;

  
public interface InstanceDao {
	
	void save(InstanceVO instance);
	
	List<InstanceVO> getAllInstances();
 
	List<InstanceVO> getInstancesByType(int typeInstance);

	void updateInstanceSetOutdated(String name);
	
	void updateInstance(InstanceVO input);
 
	InstanceVO getInstanceByName(String name);
 
}
 