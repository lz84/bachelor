package org.bachelor.context.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 项目Controller拦截器
 * @author user
 *
 */
public abstract class PrjManagedInterceptor extends ManagedInterceptor {

	protected abstract void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) ;
 

	protected abstract void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav);
 

	protected abstract boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) ;
 
	protected abstract int initOrder();
	 
}