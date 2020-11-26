/*
 * @(#)ControllerInterceptor.java	May 23, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.context.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Team Bachelor
 *
 */
public abstract class PlatManagedInterceptor extends ManagedInterceptor{
 

	protected abstract void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) ;
 

	protected abstract void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav);
 

	protected abstract boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) ;
 
	protected abstract int initOrder();
	 
	
}
