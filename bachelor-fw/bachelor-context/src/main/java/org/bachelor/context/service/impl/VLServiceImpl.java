/*
 * @(#)VariableLifecycleServiceImpl.java	May 8, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.context.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import org.bachelor.context.common.RequestUtils;
import org.bachelor.context.service.IVLService;

/**
 * @author Team Bachelor
 *
 */
@Service
public class VLServiceImpl implements IVLService {
	
	private static ThreadLocal<Map<String,Object>> tls = new ThreadLocal<Map<String, Object>>();

	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#setGloableAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setGloableAttribute(String key, Object value) {
		ServletContext sc = RequestUtils.getServletContext();
		if(sc != null){
			sc.setAttribute(key, value);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#getGloableAttribute(java.lang.String)
	 */
	@Override
	public Object getGloableAttribute(String key) {
		ServletContext sc = RequestUtils.getServletContext();
		if(sc != null){
			return sc.getAttribute(key);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#removeGloableAttribute(java.lang.String)
	 */
	@Override
	public Object removeGloableAttribute(String key) {
		ServletContext sc = RequestUtils.getServletContext();
		if(sc != null){
			Object obj = sc.getAttribute(key);
			sc.removeAttribute(key);
			return obj;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#setSessionAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setSessionAttribute(String key, Object value) {
		HttpServletRequest request = RequestUtils.getRequest();
		
		if(request == null){
			return ;
		}
		request.getSession(true).setAttribute(key, value);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#getSessionAttribute(java.lang.String)
	 */
	@Override
	public Object getSessionAttribute(String key) {
		HttpServletRequest request = RequestUtils.getRequest();
		if(request != null && request.getSession(false) != null && request.getSession().getAttribute(key) != null){
			return request.getSession().getAttribute(key);
		}else{
			return getRequestAttribute(key);

		}
		
	}


	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#removeSessionAttribute(java.lang.String)
	 */
	@Override
	public Object removeSessionAttribute(String key) {
		HttpServletRequest request = RequestUtils.getRequest();
		if(request != null){
			Object obj = request.getSession().getAttribute(key);
			request.getSession().removeAttribute(key);
			return obj;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#setRequestAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setRequestAttribute(String key, Object value) {
		HttpServletRequest request = RequestUtils.getRequest();
		if(request != null){
			request.setAttribute(key, value);
		}else{
			if(tls.get() == null){
				Map<String,Object> map = new HashMap<String,Object>();
				tls.set(map);
			}
			tls.get().put(key, value);
		}
		

	}

	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#getRequestAttribute(java.lang.String)
	 */
	@Override
	public Object getRequestAttribute(String key) {
		HttpServletRequest request = RequestUtils.getRequest();
		if(request != null){
			return request.getAttribute(key);
		}else{
			
			if(tls.get() == null){
				return null;
			}
			return tls.get().get(key);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.bachelor.vl.service.IVariableLifecycleService#removeRequestAttribute(java.lang.String)
	 */
	@Override
	public Object removeRequestAttribute(String key) {
		HttpServletRequest request = RequestUtils.getRequest();
		if(request != null){
			Object obj = request.getAttribute(key);
			request.removeAttribute(key);
			return obj;
		}
		return null;
	}

}
