package org.bachelor.bpm.domain;

import org.springframework.context.ApplicationEvent;
/**
 * 流程节点流转事件
 * @author zhangtao
 *
 */
public class TaskCompletedEvent extends ApplicationEvent{

	private static final long serialVersionUID = -5860199279957121452L;
	public <T> TaskCompletedEvent(T source) {
		super(source);
	}
}
