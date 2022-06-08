package com.diginet.springmvc.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.diginet.springmvc.entity.ModuleVO;

@Repository("moduleDao")
public class ModuleDaoImpl extends AbstractDao<Integer, ModuleVO> implements ModuleDao {

	
	@Override 
	@SuppressWarnings("all")  
	public ModuleVO findModuleById(Long id ) {		
		return (ModuleVO) getEntityManager()   
				.createQuery("from ModuleVO where idModulo = :id")
				.setParameter("id", id) 
				.getSingleResult();   	  
	}
	
	@Override
	public void installModule(String installModuleSQLPath) throws FileNotFoundException, IOException{
		FileReader file = new FileReader(installModuleSQLPath);
		BufferedReader buffer = new BufferedReader(file);
		String str = null;
		EntityManager em = getEntityManager();
		while ((str = buffer.readLine()) != null) {
			if(!str.isEmpty()){
				System.out.println("Command["+ str + "]");
				int result = em.createNativeQuery(str).executeUpdate();
				System.out.println("ret[" + result + "]");
			}				
		}
		buffer.close();
	}

	
	@Override
	@Transactional
	public void uninstallModule(String uninstallModuleSQLPath) throws FileNotFoundException, IOException {
		
		try {
			FileReader file = new FileReader(uninstallModuleSQLPath);
			BufferedReader buffer = new BufferedReader(file);
			String str = null;
			EntityManager em = getEntityManager();
			while ((str = buffer.readLine()) != null) {
				if(!str.isEmpty()){
					System.out.println("Command["+ str + "]");
					//em.getTransaction().begin();
					int result = em.createNativeQuery(str).executeUpdate();
					//em.getTransaction().commit();
					System.out.println("ret[" + result + "]");
				}				
			}
			buffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
