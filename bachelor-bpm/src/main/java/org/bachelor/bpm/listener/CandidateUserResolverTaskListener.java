package org.bachelor.bpm.listener;

import java.util.List;
import java.util.Set;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IGroupExpResolveService;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateUserResolverTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IGroupExpResolveService groupExpResolveService;
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		BaseBpDataEx bpDataEx = bpmRuntimeService.getBpDataEx(delegateTask.getProcessInstanceId(), null);
		//得到当前任务的候选人或组织
		Set<IdentityLink> ilList = delegateTask.getCandidates();
		for (IdentityLink il : ilList) {
			List<? extends IBaseEntity> users = groupExpResolveService.resolve(
					il.getGroupId(), bpDataEx);
			if (users != null && !users.isEmpty()) {
				for (IBaseEntity user : users) {
					taskService.addCandidateUser(delegateTask.getId(),
							user.getId());
				}
			}
		}

	}

}
