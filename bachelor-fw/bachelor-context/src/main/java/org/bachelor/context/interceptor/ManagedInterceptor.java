package org.bachelor.context.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public abstract class ManagedInterceptor  implements HandlerInterceptor, Ordered , IEnabled{

	/** 此拦截器是否启用**/
	private boolean enable = true;
	/** 此拦截器的顺序**/
	private int orderNum = Integer.MAX_VALUE;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e)
			throws Exception {
		if(!enable){
			return;
		}
		doAfterCompletion(request, response, handler, e);
	}

	protected abstract void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) ;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		if(!enable){
			return;
		}
		
		doPostHandle(request, response, handler, mav);
	}

	protected abstract void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav);

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if(!enable){
			return true;
		}
		return doPreHandle(request, response, obj);
	}

	protected abstract boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) ;

	@Override
	public int getOrder(){
		if(this.orderNum == Integer.MAX_VALUE){
			this.orderNum = initOrder();
		}
		return orderNum;
	}
	
	public void setOrder(int orderNum){
		this.orderNum = orderNum;
	}
	
	protected abstract int initOrder();
	
	/**
	 * @return the enable
	 */
	@Override
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}