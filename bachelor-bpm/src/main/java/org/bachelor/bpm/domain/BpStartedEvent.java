package org.bachelor.bpm.domain;

import org.springframework.context.ApplicationEvent;
/**
 * 流程开始事件
 * @author 
 *
 */
public class BpStartedEvent extends ApplicationEvent{

	private static final long serialVersionUID = -5860199279957121452L;
	public BpStartedEvent(BaseBpDataEx source) {
		super(source);
	}
}
