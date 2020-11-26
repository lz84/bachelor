package org.bachelor.bpm.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

public class BaseBpDataEx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2745897012672722024L;
	/** 业务Domain Id **/
	private String domainId;
	/** 流程实例Id **/
	private String piId;
	/** 最后一次办理人Id **/
	private String lastOptUserId;
	/** 最后一次操作Id **/
	private String lastOpt;
	/** 发起单位Id **/
	private String startCompanyId;
	/** 发起单位名称 **/
	private String startCompanyName;

	/** 发起单位简称 **/
	private String startCompanyShortName;
	
	private String[] forceAsignee;

	public String getStartCompanyShortName() {
		return startCompanyShortName;
	}

	public void setStartCompanyShortName(String startCompanyShortName) {
		this.startCompanyShortName = startCompanyShortName;
	}

	public String getStartCompanyId() {
		return startCompanyId;
	}

	public void setStartCompanyId(String startCompanyId) {
		this.startCompanyId = startCompanyId;
	}

	public String getStartCompanyName() {
		return startCompanyName;
	}

	public void setStartCompanyName(String startCompanyName) {
		this.startCompanyName = startCompanyName;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	/** 发起者Id **/
	private String startUserId;
	/** 发起者名称 **/
	private String startUserName;

	/** 当前人工节点 **/
	@Transient
	private transient TaskEx taskEx;

	@Transient
	private Map<String, Object> businessExtMap = new HashMap<String, Object>();

	public Map<String, Object> getBusinessExtMap() {
		return businessExtMap;
	}

	public void setBusinessExtMap(Map<String, Object> businessExtMap) {
		this.businessExtMap = businessExtMap;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getPiId() {
		return piId;
	}

	public void setPiId(String piId) {
		this.piId = piId;
	}

	public TaskEx getTaskEx() {
		return taskEx;
	}

	public void setTaskEx(TaskEx taskEx) {
		this.taskEx = taskEx;
		if (this.taskEx != null && this.getTaskEx().getTask() != null) {
			this.piId = this.getTaskEx().getTask().getProcessInstanceId();
		}
	}

	public String getLastOptUserId() {
		return lastOptUserId;
	}

	public void setLastOptUserId(String userId) {
		this.lastOptUserId = userId;
	}

	public String getLastOpt() {
		return lastOpt;
	}

	public void setLastOpt(String lastOpt) {
		this.lastOpt = lastOpt;
	}

	public String[] getForceAsignee() {
		return forceAsignee;
	}

	public void setForceAsignee(String[] forceAsignee) {
		this.forceAsignee = forceAsignee;
	}

}
