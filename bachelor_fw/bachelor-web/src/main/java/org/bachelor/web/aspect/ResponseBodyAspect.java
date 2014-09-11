package org.bachelor.web.aspect;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.bachelor.core.exception.BusinessException;
import org.bachelor.core.exception.SystemException;
import org.bachelor.util.ApplicationContextHolder;
import org.bachelor.web.json.JsonResponseData;
import org.bachelor.web.json.ResponseStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseBodyAspect {

	private Log log = LogFactory.getLog(this.getClass());
	
	// 标注该方法体为后置通知，当目标方法执行成功后执行该方法体
	@Around("@annotation(org.springframework.web.bind.annotation.ResponseBody) && execution(public org.bachelor.web.json.JsonResponseData *..*(..))")
	public Object jsonDataProceed(ProceedingJoinPoint pjp) {
		// 继续执行接下来的代码
		JsonResponseData ret = null;
		Object retVal = null;
		ResponseStatus status = null;
		String msg = null;
		try {
			retVal = pjp.proceed();
			status = ResponseStatus.OK;
		} catch (Throwable e) {
			if (e instanceof BusinessException) {
				status = ResponseStatus.BIZ_ERR;
			} else if (e instanceof SystemException) {
				status = ResponseStatus.SYS_ERR;
			} else {
				status = ResponseStatus.SYS_ERR;
			}
			msg = e.getMessage();
			log.error(pjp, e);
		}
		if(retVal != null) {
			ret = (JsonResponseData) retVal;
		}else{
			ret = new JsonResponseData();
		}
		ret.setMsg(msg);
		ret.setStatus(status);
		if (ret.getMsg() != null)
			ret.setMsg(ApplicationContextHolder.getApplicationContext()
					.getMessage(ret.getMsg(), null, Locale.CHINA));
		return ret;
	}
}