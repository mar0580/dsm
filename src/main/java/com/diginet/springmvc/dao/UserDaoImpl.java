package com.diginet.springmvc.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.diginet.springmvc.entity.DeviceVO;
import com.diginet.springmvc.entity.LicenceVO;
import com.diginet.springmvc.entity.User;


@Repository("userDao") 
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
  
	@SuppressWarnings("unchecked") 
	public List<DeviceVO> findAllUsers() { 
		List<DeviceVO> users = getEntityManager()     
				.createQuery("FROM DeviceVO")
				.getResultList();     
		return users;    
	}    
    
	public void save(User user) { 
		persist(user);
	} 

	public void deleteBySSO(String sso) {
		User user = (User) getEntityManager() 
				.createQuery("SELECT u FROM User u WHERE u.ssoId LIKE :ssoId")
				.setParameter("ssoId", sso) 
				.getSingleResult(); 
		delete(user); 
	}   
	  
	public List<Object> find(){   
		List<Object> objects = getEntityManager()  
				.createQuery("FROM DeviceVO")  
				.getResultList();
		return objects;     
	}
	
	//An alternative to Hibernate.initialize() 
	protected void initializeCollection(Collection<?> collection) { 
	    if(collection == null) {
	        return; 
	    }
	    collection.iterator().hasNext();
	}

}
