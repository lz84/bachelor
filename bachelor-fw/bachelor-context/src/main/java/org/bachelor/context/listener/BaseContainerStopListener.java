package org.bachelor.context.listener;

import javax.persistence.criteria.Order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseContainerStopListener implements ApplicationListener<ContextClosedEvent>,Order {
 
	private Log log = LogFactory.getLog(BaseContainerStopListener.class);
	
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		System.out.println(event.getSource());
		try{
			onEvent(event);
		}catch(Exception e){
			//打印异常信息
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Context容器关闭时触发的事件
	 * @param event
	 * @throws Exception
	 */
	public abstract void onEvent(ContextClosedEvent event) throws Exception;
	
}
