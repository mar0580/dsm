package com.diginet.springmvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "tbl_dsm_tasks")  
public class DsmTasksVO {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id; 
	@Column 
	private int dsmTaskId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) { 
		this.id = id;
	}
	public int getDsmTaskId() {
		return dsmTaskId;
	}
	public void setDsmTaskId(int dsmTaskId) { 
		this.dsmTaskId = dsmTaskId; 
	} 
} 
