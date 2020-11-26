package org.bachelor.bpm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.service.IExpressionResolver;
import org.bachelor.bpm.service.IBpmRepositoryService;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmRepositoryServiceImpl implements IBpmRepositoryService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;
	@Autowired
	private IExpressionResolver authService;

	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public List<ProcessDefinition> getAllPd() {
		List<ProcessDefinition> processDedinition = repositoryService
				.createProcessDefinitionQuery().orderByProcessDefinitionName()
				.asc().orderByProcessDefinitionVersion().desc().list();
		return processDedinition;
	}

	@Override
	public ProcessDefinition getPdById(String pdId) {
		ProcessDefinition processDedinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(pdId)
				.singleResult();
		return processDedinition;
	}

	@Override
	public List<ProcessDefinition> getAllLastedPd() {
		List<ProcessDefinition> processDedinition = repositoryService
				.createProcessDefinitionQuery().latestVersion().list();
		return processDedinition;
	}

	@Override
	public List<Task> getAllTask(String pdId) {
		return taskService.createTaskQuery().processDefinitionId(pdId).list();
	}

	@Override
	public List<HistoricProcessInstance> queryProcess(
			String processDefinitionId, String taskDefinitionKey,
			String startedOrgId, Date startTime, Date endTime) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery().unfinished();
		if (processDefinitionId != null && !"".equals(processDefinitionId)) {
			query.processDefinitionId(processDefinitionId);
		}
		if (startTime != null && !"".equals(startTime)) {
			query.startedAfter(startTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			query.startedAfter(endTime);
		}
		if (!StringUtils.isEmpty(startedOrgId)) {
			query.variableValueEquals("startCompanyId", startedOrgId);
		}
		List<HistoricProcessInstance> hPiList = query.list();
		if (hPiList == null || hPiList.size() < 1)
			return null;
		List<HistoricProcessInstance> temphPis = new ArrayList<HistoricProcessInstance>();
		for (HistoricProcessInstance pi : hPiList) {
			// 获取流程的当前节点
			// 执行实例
			ExecutionEntity execution = (ExecutionEntity) runtimeService
					.createProcessInstanceQuery().processInstanceId(pi.getId())
					.singleResult();
			String activityId = execution.getActivityId();
			// 判断流程是否包含选定的节点定义key
			if (taskDefinitionKey != null
					&& StringUtils.isNotEmpty(taskDefinitionKey)
					&& !taskDefinitionKey.equals(activityId)) {
				// 将不满足条件的流程从集合中移除
				temphPis.add(pi);
			}
			if (!StringUtils.isEmpty(startedOrgId)
					&& !StringUtils.isEmpty(pi.getStartUserId())) {
				String userId = pi.getStartUserId();
				IBaseEntity user = authService.resolveUserByUserExp(userId);
//				if (user != null && !startedOrgId.equals(user.getOrgId())) {
//					temphPis.add(pi);
//				}
			}
		}
		// 将不满足条件的流程从集合中移除
		hPiList.removeAll(temphPis);
		if (hPiList.size() > 0) {
			return hPiList;
		} else {
			return null;
		}
	}

	@Override
	public byte[] getProcessDiagram(String pdId) {
		if (StringUtils.isBlank(pdId)) {
			return null;
		}
		InputStream is = null;
		try {
			is = repositoryService.getProcessDiagram(pdId);
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			return bytes;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public List<TaskDefinition> getAllTaskDefinition(String bizKey) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		List<TaskDefinition> taskList = new ArrayList<TaskDefinition>();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(pi.getProcessDefinitionId());
		// 获得当前任务的所有节点
		List<ActivityImpl> activitiList = def.getActivities();
		for (ActivityImpl activityImpl : activitiList) {
			if ("userTask".equals(activityImpl.getProperty("type"))) {
				Object obj = activityImpl.getProperty("taskDefinition");
				if (obj instanceof TaskDefinition) {
					TaskDefinition taskDefinition = (TaskDefinition) obj;
					taskList.add(taskDefinition);
				}
			}
		}
		return taskList;
	}

	@Override
	public DiagramLayout getProcessDiagramLayout(String pdId) {
		DiagramLayout layout = repositoryService.getProcessDiagramLayout(pdId);
		return layout;
	}

	@Override
	public List<ActivityImpl> getProcessActivitImpl(String bizKey) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		List<TaskDefinition> taskList = new ArrayList<TaskDefinition>();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(pi.getProcessDefinitionId());
		// 获得当前任务的所有节点
		List<ActivityImpl> activitiList = def.getActivities();
		return activitiList;
	}

}
