package com.diginet.springmvc.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_license")
public class LicenceVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String requisition_hash;
	@Column
	private Date date_solicitation;
	@Column
	private String license_key;
	@Column
	private Date date_license;
	@Column
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequisition_hash() {
		return requisition_hash;
	}

	public void setRequisition_hash(String requisition_hash) {
		this.requisition_hash = requisition_hash;
	}

	public Date getDate_solicitation() {
		return date_solicitation;
	}

	public void setDate_solicitation(Date date_solicitation) {
		this.date_solicitation = date_solicitation;
	}

	public String getLicense_key() {
		return license_key;
	}

	public void setLicense_key(String license_key) {
		this.license_key = license_key;
	}

	public Date getDate_license() {
		return date_license;
	}

	public void setDate_license(Date date_license) {
		this.date_license = date_license;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
