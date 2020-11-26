package org.bachelor.bpm.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import org.bachelor.bpm.common.BpmConstant;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpStartedEvent;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.context.service.IVLService;

@Service
public class BpStartedListener implements ApplicationListener<BpStartedEvent>{
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	
	@Override
	public void onApplicationEvent(BpStartedEvent event) {
//		BaseBpDataEx  bpDataEx=(BaseBpDataEx) event.getSource();
//		BaseBpDataEx newBpDateEx = bpmRuntimeService.getBpDataEx(bpDataEx.getPiId(), 
//				bpDataEx.getLastOptUserId());
//		vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY, newBpDateEx);
	}
	
}
