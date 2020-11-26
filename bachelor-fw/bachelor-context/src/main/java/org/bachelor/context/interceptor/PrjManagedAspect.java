package org.bachelor.context.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public abstract class PrjManagedAspect extends ManageAspect {
  
	@Around("!execution(public * org.bachelor.**.**..**.*(..)) and execution(public * org.bachelor.**.**.biz..*Biz*.*(..)) or !execution(public * org.bachelor.**.**.**..**.*(..)) and execution(public * org.bachelor.**.**.dao..*Dao*.*(..))")
	  public Object intercept(ProceedingJoinPoint pjp) throws Throwable{
			
		return doIntercept(pjp);
	}
	 
}