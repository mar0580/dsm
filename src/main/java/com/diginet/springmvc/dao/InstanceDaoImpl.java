package com.diginet.springmvc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.diginet.springmvc.entity.InstanceVO;
@Repository ("instanceDao")
public class InstanceDaoImpl  extends AbstractDao<Integer, InstanceVO> implements InstanceDao{

	@Override 
	public void save(InstanceVO instance) { 
		save(instance); 
	} 

	@Override  
	@SuppressWarnings("all") 
	public List<InstanceVO> getAllInstances() {  
		return (List<InstanceVO>) getEntityManager()   
				.createQuery("from InstanceVO")  
				.getResultList();   	  
	}

	@Override 
	@SuppressWarnings("all")  
	public List<InstanceVO> getInstancesByType(int typeInstance ) {		
		return (List<InstanceVO>) getEntityManager()   
				.createQuery("from InstanceVO where instance_type = :type")
				.setParameter("type", typeInstance) 
				.getResultList();   	  
	}  
	 
	@Override 
	@SuppressWarnings("all")
	public void updateInstanceSetOutdated( String name) {	 
			
			 InstanceVO oldInst = (InstanceVO) getEntityManager().createQuery("from InstanceVO where instanceName =:name")
			 .setParameter("name", name) 
			 .getSingleResult();     
			 oldInst.setInstanceStatus(0);  
			 update(oldInst); 
	}
 
	@Override
	@SuppressWarnings("all") 
	public void updateInstance( InstanceVO input ) { 
		EntityManager em = getEntityManager();
		Query q = em.createQuery("from InstanceVO where instance_name =:oldName");
		q.setParameter("oldName", input.getInstanceName());	
		InstanceVO oldInstance = (InstanceVO) q.getSingleResult();  
		int id = oldInstance.getId();
		oldInstance = input;  
		oldInstance.setId(id);   
		update(oldInstance);      
	}  

	@Override
	public InstanceVO getInstanceByName(String name) {		
		return (InstanceVO) getEntityManager().createQuery("from InstanceVO where instance_name =:name")  
				.setParameter("name", name)
				.getSingleResult();
	} 
}
