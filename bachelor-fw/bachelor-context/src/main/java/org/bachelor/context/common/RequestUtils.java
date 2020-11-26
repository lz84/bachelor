/*
 * @(#)ContextHelper.java	May 9, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.context.common;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Team Bachelor
 *
 */
public class RequestUtils {
	
	private static ServletContext servletContext = null;
	private static ServletConfig servletConfig = null;

	public static HttpServletRequest getRequest(){
		ServletRequestAttributes ra = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes());
		if(ra == null){
			return null;
		}
		
		HttpServletRequest request = ra.getRequest();
		return request;
	}
	
	
	public static ServletContext getServletContext(){
		return servletContext;
	}

	
	public static void setServletContext(ServletContext sc){
		servletContext = sc;
	}


	public static ServletConfig getServletConfig() {
		return servletConfig;
	}


	public static void setServletConfig(ServletConfig sc) {
		servletConfig = sc;
	}
	
	
}
