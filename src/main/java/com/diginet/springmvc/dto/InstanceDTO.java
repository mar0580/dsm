package com.diginet.springmvc.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.diginet.springmvc.entity.InstanceVO;

public class InstanceDTO {
		 
		private Integer id;
		private int instance_type;
		private String instance_name;
		private int instance_status;
		private int jvm_initmemory;
		private int jvm_maxmemory;
		private int log_file_maxsize;
		private int log_file_maxfiles;
		private int log_file_level;
		private String path_export_support_logs;
		private String server_ip;
		private int server_port;
		private int server_keep_alive_interval;
		private int remote_server_comm_type; 
		private String remote_server_url;
		private String remote_server_ip;
		private Integer remote_server_port;
		private String remote_server_token;
		private String remote_server_user;
		private String remote_server_user_passwd;
		private String remote_server_certificate;
		private Integer remote_server_id; 
		private Integer remote_server_keep_alive_interval;
		private Integer remote_server_events_interval;
		private String path_import_person;
		private String path_import_company;
		private String path_import_pendrive_operations;
		private String path_export_files;
		private int concurrent_proc_task;
		private int task_saving_days;  
		private int event_collect_interval;
		private String path_import_events;
		private int concurrent_time_event_files;
		private int concurrent_proc_files; 
		private int concurrent_numb_event_files;  
		private int saving_event_days;  
		private String summer_time_start; 
		private String summer_time_end;
		private int deviceLimit;
		
		public InstanceDTO() {
			
		}   
		  

		public InstanceVO getInstanceVO() throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			InstanceVO instance = new InstanceVO(this.id, this.instance_type, this.instance_name,
					this.instance_status, this.jvm_initmemory, this.jvm_maxmemory,
					this.log_file_maxsize, this.log_file_maxfiles, this.log_file_level,
					this.path_export_support_logs, sdf.parse(this.summer_time_start),
					sdf.parse(this.summer_time_end), this.server_ip, this.server_port,
					this.server_keep_alive_interval, this.remote_server_comm_type,
					this.remote_server_url, this.remote_server_ip,
					this.remote_server_port, this.remote_server_token,
					this.remote_server_user, this.remote_server_user_passwd,
					this.remote_server_certificate, this.remote_server_id,
					this.remote_server_keep_alive_interval,
					this.remote_server_events_interval, this.path_import_person,
					this.path_import_company, this.path_import_pendrive_operations,
					this.path_export_files, this.concurrent_proc_task,
					this.task_saving_days, this.event_collect_interval,
					this.path_import_events, this.concurrent_time_event_files,
					this.concurrent_proc_files, this.concurrent_numb_event_files,
					this.saving_event_days, this.deviceLimit);
			return instance;
		}

		public Integer getId() {
			return this.id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public int getDeviceLimit() {
			return deviceLimit;
		}
		
		public void setDeviceLimit(int deviceLimit) {
			this.deviceLimit = deviceLimit;
		}

		 
		public int getInstanceType() { 
			return this.instance_type;
		}
	 
		public void setInstanceType(int instanceType) {
			this.instance_type = instanceType;
		}

		
		public String getInstanceName() {
			return this.instance_name;
		}

		public void setInstanceName(String instanceName) {
			this.instance_name = instanceName;
		}

		
		public int getInstanceStatus() {
			return this.instance_status;
		}

		public void setInstanceStatus(int instanceStatus) {
			this.instance_status = instanceStatus;
		}

		
		public int getJvmInitmemory() {
			return this.jvm_initmemory; 
		}

		public void setJvmInitmemory(int jvmInitmemory) {
			this.jvm_initmemory = jvmInitmemory;
		}

		
		public int getJvmMaxMemory() { 
			return this.jvm_maxmemory;
		}

		public void setJvmMaxMemory(int jvmMaxMemory) {
			this.jvm_maxmemory = jvmMaxMemory;
		}

		
		public int getLogFileMaxsize() {
			return this.log_file_maxsize;
		}

		public void setLogFileMaxsize(int logFileMaxsize) {
			this.log_file_maxsize = logFileMaxsize;
		}

		
		public int getLogFileMaxfiles() {
			return this.log_file_maxfiles;
		}

		public void setLogFileMaxfiles(int logFileMaxfiles) {
			this.log_file_maxfiles = logFileMaxfiles;
		}

		
		public int getLogFileLevel() {
			return this.log_file_level;
		}

		public void setLogFileLevel(int logFileLevel) {
			this.log_file_level = logFileLevel;
		}

		
		public String getPathExportSupportLogs() {
			return this.path_export_support_logs;
		}

		public void setPathExportSupportLogs(String pathExportSupportLogs) {
			this.path_export_support_logs = pathExportSupportLogs;
		}
		
		public String getServerIp() { 
			return this.server_ip;
		}

		public void setServerIp(String serverIp) {
			this.server_ip = serverIp;
		}

		
		public int getServerPort() {
			return this.server_port;
		}

		public void setServerPort(int serverPort) {
			this.server_port = serverPort;
		}

		
		public int getServerKeepAliveInterval() {
			return this.server_keep_alive_interval;
		}

		public void setServerKeepAliveInterval(int serverKeepAliveInterval) {
			this.server_keep_alive_interval = serverKeepAliveInterval;
		}

		
		public int getRemoteServerCommType() {
			return this.remote_server_comm_type;
		}

		public void setRemoteServerCommType(int remoteServerCommType) {
			this.remote_server_comm_type = remoteServerCommType;
		}

		
		public String getRemoteServerUrl() {
			return this.remote_server_url;
		}

		public void setRemoteServerUrl(String remoteServerUrl) {
			this.remote_server_url = remoteServerUrl;
		}

		
		public String getRemoteServerIp() {
			return this.remote_server_ip;
		}

		public void setRemoteServerIp(String remoteServerIp) {
			this.remote_server_ip = remoteServerIp;
		}

		
		public Integer getRemoteServerPort() {
			return this.remote_server_port;
		}

		public void setRemoteServerPort(Integer remoteServerPort) {
			this.remote_server_port = remoteServerPort;
		}

		
		public String getRemoteServerToken() {
			return this.remote_server_token;
		}

		public void setRemoteServerToken(String remoteServerToken) {
			this.remote_server_token = remoteServerToken;
		}

		
		public String getRemoteServerUser() {
			return this.remote_server_user;
		}

		public void setRemoteServerUser(String remoteServerUser) {
			this.remote_server_user = remoteServerUser;
		} 

		
		public String getRemoteServerUserPasswd() {
			return this.remote_server_user_passwd;
		}

		public void setRemoteServerUserPasswd(String remoteServerUserPasswd) {
			this.remote_server_user_passwd = remoteServerUserPasswd;
		}

		
		public String getRemoteServerCertificate() {
			return this.remote_server_certificate;
		}

		public void setRemoteServerCertificate(String remoteServerCertificate) {
			this.remote_server_certificate = remoteServerCertificate;
		}

		
		public Integer getRemoteServerId() {
			return this.remote_server_id;
		}

		public void setRemoteServerId(Integer remoteServerId) {
			this.remote_server_id = remoteServerId;
		}

		
		public Integer getRemoteServerKeepAliveInterval() {
			return this.remote_server_keep_alive_interval;
		}

		public void setRemoteServerKeepAliveInterval(Integer remoteServerKeepAliveInterval) {
			this.remote_server_keep_alive_interval = remoteServerKeepAliveInterval;
		}

		
		public Integer getRemoteServerEventsInterval() {
			return this.remote_server_events_interval;
		}

		public void setRemoteServerEventsInterval(Integer remoteServerEventsInterval) {
			this.remote_server_events_interval = remoteServerEventsInterval;
		}

		
		public String getPathImportPerson() {
			return this.path_import_person;
		}

		public void setPathImportPerson(String pathImportPerson) {
			this.path_import_person = pathImportPerson;
		}

		
		public String getPathImportCompany() {
			return this.path_import_company;
		}

		public void setPathImportCompany(String pathImportCompany) {
			this.path_import_company = pathImportCompany;
		}

		
		public String getPathImportPendriveOperations() {
			return this.path_import_pendrive_operations; 
		}
	 
		public void setPathImportPendriveOperations(String pathImportPendriveOperations) {
			this.path_import_pendrive_operations = pathImportPendriveOperations;
		}

		
		public String getPathExportFiles() {   
			return this.path_export_files;   
		} 

		public void setPathExportFiles(String pathExportFiles) {
			this.path_export_files = pathExportFiles;
		}

		
		public int getConcurrentProcTask() {
			return this.concurrent_proc_task;
		}

		public void setConcurrentProcTask(int concurrentProcTask) {
			this.concurrent_proc_task = concurrentProcTask;
		}

		
		public int getTaskSavingDays() {
			return this.task_saving_days;
		}

		public void setTaskSavingDays(int taskSavingDays) {
			this.task_saving_days = taskSavingDays;
		}

		
		public int getEventCollectInterval() {
			return this.event_collect_interval;
		}

		public void setEventCollectInterval(int eventCollectInterval) {
			this.event_collect_interval = eventCollectInterval;
		}

		
		public String getPathImportEvents() { 
			return this.path_import_events;
		}
	 
		public void setPathImportEvents(String pathImportEvents) {
			this.path_import_events = pathImportEvents;
		}

		
		public int getConcurrentTimeEventFiles() {
			return this.concurrent_time_event_files;
		}

		public void setConcurrentTimeEventFiles(int concurrentTimeEventFiles) {
			this.concurrent_time_event_files = concurrentTimeEventFiles;
		}

		
		public int getConcurrentProcFiles() {
			return this.concurrent_proc_files;
		}

		public void setConcurrentProcFiles(int concurrentProcFiles) {
			this.concurrent_proc_files = concurrentProcFiles;
		}
	  
		
		public int getConcurrentNumbEventFiles() {
			return this.concurrent_numb_event_files;
		} 

		public void setConcurrentNumbEventFiles(int concurrentNumbEventFiles) {
			this.concurrent_numb_event_files = concurrentNumbEventFiles;
		}

		
		public int getSavingEventDays() {
			return this.saving_event_days;
		}
	 
		public void setSavingEventDays(int savingEventDays) {
			this.saving_event_days = savingEventDays;
		}
	  
		
		public String getSummer_time_start(){
			return this.summer_time_start; 
		}
		 
		public void setSummer_time_start(String summer_time_start){
			this.summer_time_start = summer_time_start; 
		}
		
		public String getSummer_time_end(){ 
			return this.summer_time_end;  
		} 
		
		public void setSummer_time_end(String summer_time_end){
			this.summer_time_end = summer_time_end; 
		} 

}