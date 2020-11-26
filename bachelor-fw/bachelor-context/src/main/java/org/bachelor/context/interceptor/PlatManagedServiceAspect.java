package org.bachelor.context.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public abstract class PlatManagedServiceAspect extends ManageAspect {
  
	@Around("execution(public * org.bachelor.**.service..*Service*.*(..))")
	  public Object intercept(ProceedingJoinPoint pjp) throws Throwable{
		
		return doIntercept(pjp);
	}
}
