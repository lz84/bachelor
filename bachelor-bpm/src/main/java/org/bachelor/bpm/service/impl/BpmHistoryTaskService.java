package org.bachelor.bpm.service.impl;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmHistoryTaskService;
import org.bachelor.bpm.vo.TaskInstanceVo;

@Service
public class BpmHistoryTaskService implements IBpmHistoryTaskService {

	@Autowired
	private HistoryService historyService;
	@Autowired
	private FormService formService;
	@Autowired
	private TaskService taskService;

	@Override
	public TaskEx getPreTask(String taskId) {
		HistoricTaskInstanceQuery query = historyService
				.createHistoricTaskInstanceQuery().taskId(taskId);
		HistoricTaskInstance hti = query.singleResult();
		Task hiTask = taskService.createTaskQuery().taskId(hti.getId())
				.singleResult();
		return null;
	}

	@Override
	public List<HistoricTaskInstance> getFinishedTaskByPiid(
			String processInstanceId) {
		List<HistoricTaskInstance> hti2 = historyService
				.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).finished().orderByHistoricTaskInstanceEndTime().asc().list();

		return hti2;
	}

	@Override
	public HistoricTaskInstance queryTaskInstanceVo(String piid, String taskDefKey) {
		HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(piid).taskDefinitionKey(taskDefKey).singleResult();
		return hti;
	}

	@Override
	public List<HistoricTaskInstance> getHistoricTasks(String piid,
			String taskDefKey) {
		List<HistoricTaskInstance> htis = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(piid).finished().taskDefinitionKey(taskDefKey).orderByHistoricTaskInstanceEndTime().asc().list();
		return htis;
	}
	
	

}
