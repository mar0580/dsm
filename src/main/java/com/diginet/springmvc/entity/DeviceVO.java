package com.diginet.springmvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.digicon.model.InstanceVO;

@Entity 
@Table(name = "tbl_device")  
public class DeviceVO implements Serializable {

	/** 
     * 
     */ 
	private static final long serialVersionUID = 7606293142237801762L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;
	@Column
	private String mrp;
	@Column
	private String ip;
	
	@Column
	private Integer barcode;
	@Column
	private Integer mifare;
	@Column
	private Integer rfid;
	@Column
	private Integer sagem;
	@Column
	private Integer suprema;

	@Column
	private Long company_id;
	@Column
	private Long place_id;

	@Column
	private Long userInsert_Id;
	@Column
	private Long userUpdate_Id;
	@Column
	private Date insertData;
	@Column
	private Date updateData;

	@Column
	private Date date_last_authentication;

	@Column
	private Integer validation;
	@Column
	private Integer status;
	@Column
	private Integer active;
	@Column 
	private Integer device_type;
 
	@Column
	private Integer qt_employee;
	@Column
	private Integer qt_card;
	@Column
	private Integer qt_bio;
	@Column
	private Integer flash_total;
	@Column
	private Integer flash_used;
	@Column
	private Integer mrp_total;
	@Column
	private Integer mrp_used;
	@Column
	private String firmware_version;
	@Column
	private Date date_last_update;

	
	@Column
	private Integer reg_type2;
	@Column
	private Integer reg_type3;
	@Column
	private Integer reg_type4;
	@Column
	private Integer reg_type5;
	
	@Column
	private String prt_version;
	@Column
	private String prt_level;
	@Column
	private Integer prt_tickets;
	@Column
	private Integer prt_rim;
	@Column
	private String netmask;
	@Column
	private Integer port;
	@Column
	private String gateway;
	@Column
	private Integer tm_reconnect;
	@Column
	private Integer tm_timeout;
	@Column
	private Integer comm_type;  
	
	private transient InstanceVO instance;
 
	@ManyToOne(fetch = FetchType.LAZY)   
	@JoinColumn(name = "instance_id", nullable = false)   
	public InstanceVO getInstance() {
		return instance; 
	}

	public void setInstance(InstanceVO instance) {
		this.instance = instance;
	}
	
	public Long getIdDevice() {
		return id;
	}

	public void setIdDevice(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getBarcode() {

		if (barcode == null)
			barcode = 0;

		return barcode;

	}

	public void setBarcode(Integer barcode) {
		this.barcode = barcode;
	}

	public Integer getMifare() {

		if (mifare == null)
			mifare = 0;

		return mifare;
	}

	public void setMifare(Integer mifare) {
		this.mifare = mifare;
	}

	public Integer getRfid() {

		if (rfid == null)
			rfid = 0;

		return rfid;
	}

	public void setRfid(Integer rfid) {
		this.rfid = rfid;
	}

	public Integer getSagem() {

		if (sagem == null)
			sagem = 0;

		return sagem;
	}

	public void setSagem(Integer sagem) {
		this.sagem = sagem;
	}

	public Integer getSuprema() {

		if (suprema == null)
			suprema = 0;

		return suprema;
	}

	public void setSuprema(Integer suprema) {
		this.suprema = suprema;
	}

	public Long getIdCompany() {
		return company_id;
	}

	public void setIdCompany(Long company_id) {
		this.company_id = company_id;
	}

	public Long getIdPlace() {
		return place_id;
	}

	public void setIdPlace(Long place_id) {
		this.place_id = place_id;
	}
 
	public Long getUserInsertId() {
		return userInsert_Id;
	}

	public void setUserInsertId(Long userInsert_Id) {
		this.userInsert_Id = userInsert_Id;
	}

	public Long getUserUpdateId() {
		return userUpdate_Id;
	}
  
	public void setUserUpdateId(Long userUpdate_Id) {
		this.userUpdate_Id = userUpdate_Id;
	}

	public Date getInsertData() {
		return insertData;
	}

	public void setInsertData(Date insertData) {
		this.insertData = insertData;
	}

	public Date getUpdateData() {
		return updateData;
	}

	public void setUpdateData(Date updateData) {
		this.updateData = updateData;
	}


	public Integer getValidation() {
		return validation;
	}

	public void setValidation(Integer validation) {
		this.validation = validation;
	}

	public Date getDateLastAuthentication() {
		return date_last_authentication;
	}

	public void setDateLastAuthentication(Date date_last_authentication) {
		this.date_last_authentication = date_last_authentication;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQtEmployee() {
		return qt_employee;
	}

	public void setQtEmployee(Integer qt_employee) {
		this.qt_employee = qt_employee;
	}

	public Integer getQtCard() {
		return qt_card;
	}

	public void setQtCard(Integer qt_card) {
		this.qt_card = qt_card;
	}

	public Integer getQtBio() {
		return qt_bio;
	}

	public void setQtBio(Integer qt_bio) {
		this.qt_bio = qt_bio;
	}

	public Integer getFlashTotal() {
		return flash_total;
	}

	public void setFlashTotal(Integer flash_total) {
		this.flash_total = flash_total;
	}

	public Integer getFlashUsed() {
		return flash_used;
	}

	public void setFlashUsed(Integer flash_used) {
		this.flash_used = flash_used;
	}

	public Integer getMrpTotal() {
		return mrp_total;
	}

	public void setMrpTotal(Integer mrp_total) {
		this.mrp_total = mrp_total;
	}

	public Integer getMrpUsed() {
		return mrp_used;
	}

	public void setMrpUsed(Integer mrp_used) {
		this.mrp_used = mrp_used;
	}

	public String getFirmwareVersion() {
		return firmware_version;
	}

	public void setFirmwareVersion(String firmware_version) {
		// only update database firmware when version sent by device matches mask XX.XX.XX
		// matches cases: 2.0.1 , 67.1.55 , 6.22.3
		// don't matches cases: 2.1 , 3.0.125 , 10 , PIS0873 , PI.4.2 , 3.0.4.2
		if (firmware_version == null || firmware_version.matches("^(\\d{1,2}\\.\\d{1,2}\\.\\d{1,2})$")) {
			this.firmware_version = firmware_version;
		}
	}

	public Date getDateLastUpdate() { 
		return date_last_update;
	}

	public void setDateLastUpdate(Date date_last_update) {
		this.date_last_update = date_last_update; 
	}

	public Integer getRegType2() {
		return reg_type2;
	}

	public void setRegType2(Integer reg_type2) {
		this.reg_type2 = reg_type2;
	}

	public Integer getRegType3() {
		return reg_type3;
	}

	public void setRegType3(Integer reg_type3) {
		this.reg_type3 = reg_type3;
	}

	public Integer getRegType4() {
		return reg_type4;
	}

	public void setRegType4(Integer reg_type4) {
		this.reg_type4 = reg_type4;
	}

	public Integer getRegType5() {
		return reg_type5;
	}

	public void setRegType5(Integer reg_type5) {
		this.reg_type5 = reg_type5;
	}

	public Integer getMrpLivre() { 
		return (mrp_total - mrp_used); 
	}

	public Integer getFlashLivre() {
		return (flash_total - flash_used);
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getDeviceTtype() {
		return device_type;
	}

	public void setDeviceType(Integer device_type) {
		this.device_type = device_type;
	}

	public String getPrtVersion() {
		if (prt_version == null)
			return "";
		
		return prt_version;
	}

	public void setPrtVersion(String prt_version) {
		this.prt_version = prt_version;
	}

	public String getPrtLevel() {
		if (prt_level == null)
			prt_level  = "";
		
		return prt_level ;
	}

	public void setPrtLevel(String prt_level) {
		this.prt_level  = prt_level;
	}

	public Integer getPrtTickets() {
		if (prt_tickets == null)
			prt_tickets  = 0;
		
		return prt_tickets ;
	}

	public void setPrtTickets(Integer prt_tickets ) {
		this.prt_tickets  = prt_tickets ;
	}

	public Integer getPrtRim() {
		return prt_rim;
	}

	public void setPrtRim(Integer prt_rim) {
		this.prt_rim = prt_rim;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Integer getTmReconnect() {
		return tm_reconnect;
	}

	public void setTmReconnect(Integer tm_reconnect) {
		this.tm_reconnect = tm_reconnect;
	}

	public Integer getTmTimeout() {
		return tm_timeout;
	}

	public void setTmTimeout(Integer tm_timeout) {
		this.tm_timeout = tm_timeout;
	}

	public Integer getCommType() { 
		return comm_type; 
	}
 
	public void setCommType(Integer comm_type) {
		this.comm_type = comm_type;
	}
		
}
