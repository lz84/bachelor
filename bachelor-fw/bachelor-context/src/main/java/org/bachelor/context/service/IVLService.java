/*
 * @(#)IVariableLifecycleService.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.context.service;

/**
 * 变量生命周期管理服务
 * 
 * @author Team Bachelor
 *
 */
public interface IVLService {


	public void setGloableAttribute(String key, Object value);
	public Object getGloableAttribute(String key);
	public Object removeGloableAttribute(String key);
	
	public void setSessionAttribute(String key, Object value);
	public Object getSessionAttribute(String key);
	public Object removeSessionAttribute(String key);
	
	public void setRequestAttribute(String key, Object value);
	public Object getRequestAttribute(String key);
	public Object removeRequestAttribute(String key);
	

}
