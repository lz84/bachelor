package org.bachelor.context.listener;

import javax.persistence.criteria.Order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseContainerStartupListener implements ApplicationListener<ContextRefreshedEvent>,Order{

	private Log log = LogFactory.getLog(BaseContainerStartupListener.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println(event.getSource());
		try{
			onEvent(event);
		}catch(Exception e){
			//打印异常信息
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Context容器启动时触发的事件
	 * @param event
	 * @throws Exception
	 */
	public abstract void onEvent(ContextRefreshedEvent event) throws Exception;
}
