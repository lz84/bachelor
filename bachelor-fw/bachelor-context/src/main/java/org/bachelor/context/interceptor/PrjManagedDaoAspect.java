package org.bachelor.context.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public abstract class PrjManagedDaoAspect extends ManageAspect {
  
	@Around("!execution(public * org.bachelor.**.**.**..**.*(..)) and execution(public * org.bachelor.**.**.dao..*Dao*.*(..))")
	  public Object intercept(ProceedingJoinPoint pjp) throws Throwable{
		 
		return doIntercept(pjp);
	}
}
