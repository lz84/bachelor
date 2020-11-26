package org.bachelor.context.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.Ordered;

public abstract class ManageAspect  implements Ordered,IEnabled {
 
	/** 此拦截器是否启用**/
	private boolean enable = true;
	/** 此拦截器的顺序**/
	private int orderNum = Integer.MAX_VALUE;
	
	/**
	 * 得到拦截加载顺序
	 */
	@Override
	public int getOrder() {
		if(this.orderNum == Integer.MAX_VALUE){
			this.orderNum = initOrder();
		}
		return orderNum;
	}
	
	@Override
	public boolean isEnable() {
		return enable;
	}
	
	@Override
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
	protected abstract int initOrder();
	
	protected abstract boolean doPre(Object target,Object method,Object[] args) ;
	
	protected abstract boolean doPost(Object target,Object method,Object[] args) ;
	
	
	protected Object doIntercept(ProceedingJoinPoint pjp) throws Throwable{
		if(isEnable()==false){
			
			return pjp.proceed();
		}
		//目标类
		Object target = pjp.getTarget();
		//目标类方法参数
		Object[] args = pjp.getArgs();
		//目标类方法
		Object method = pjp.getSignature();
		//方法执行之前操作
		doPre(target,method,args);
		//执行方法
		Object returnObj = pjp.proceed();
		//执行方法之后
		doPost(target,method,args);
		//返回对象
		return returnObj;
	}
}