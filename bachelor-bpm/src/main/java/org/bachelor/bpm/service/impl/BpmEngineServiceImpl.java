package org.bachelor.bpm.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.form.FormPropertyHandler;
import org.activiti.engine.impl.javax.el.Expression;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.domain.TaskType;
import org.bachelor.bpm.service.IBpmEngineService;

@Service
public class BpmEngineServiceImpl implements IBpmEngineService {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;

	@Override
	public void warpTaskDefMap(Map<PvmTransition, ActivityImpl> actImplMap,
			Map<PvmTransition, TaskDefinition> taskDefMap) {
		if (actImplMap == null || actImplMap.keySet().size() == 0)
			taskDefMap = null;
		for (PvmTransition pvmTran : actImplMap.keySet()) {
			taskDefMap.put(pvmTran, (TaskDefinition) actImplMap.get(pvmTran)
					.getProperty("taskDefinition"));
		}
	}

	@Override
	public String getMIAssigneeListName(ActivityImpl actImpl) {
		if (actImpl == null)
			return null;
		ActivityBehavior activitBehavopr;
		String MIAssigneeListName = null;
		activitBehavopr = actImpl.getActivityBehavior();
		MultiInstanceActivityBehavior paraMulti = (MultiInstanceActivityBehavior) activitBehavopr;
		String expressionText = paraMulti.getCollectionExpression()
				.getExpressionText();
		MIAssigneeListName = StringUtils.substringBeforeLast(
				StringUtils.substringAfterLast(expressionText, "{"), "}");
		return MIAssigneeListName;
		}

	@Override
	public ActivityImpl getOutGoingActivityImpl(String bizKey,
			String outGoingTransValue) {
		ProcessInstance pi = getProcessInstancsByBizKey(bizKey);
		if (outGoingTransValue == null || outGoingTransValue.isEmpty())
			return null;
		Map<PvmTransition, ActivityImpl> pvmTrans = this
				.getNextActivityImpl(bizKey);
		ActivityImpl outGoingActivityImpl = null;
		for (PvmTransition trans : pvmTrans.keySet()) {
			// 当节点出线唯一时，直接返回该终点对象
			if (pvmTrans.keySet().size() == 1)
				return pvmTrans.get(trans);
			// 节点出线不唯一时，解析出线表达式，判断节点流转的终点实例
			String pvmTransitionExp = (String) trans
					.getProperty("conditionText");
			if (pvmTransitionExp != null && !pvmTransitionExp.isEmpty()) {
				pvmTransitionExp = pvmTransitionExp.toString().trim();
				String variableValue = StringUtils.substringBeforeLast(
						StringUtils.substringAfterLast(pvmTransitionExp, "=="),
						"}");
				if (variableValue.contains(outGoingTransValue)) {
					outGoingActivityImpl = pvmTrans.get(trans);
				}
			}
		}
		if (outGoingActivityImpl == null) {
			return null;
		} else {
			return outGoingActivityImpl;
		}
	}

	@Override
	public Map<PvmTransition, ActivityImpl> getNextActivityImpl(String bizKey) {
		ProcessInstance pi = getProcessInstancsByBizKey(bizKey);
		// 获取该全部的流程活动实例
		List<Execution> excutionList = runtimeService.createExecutionQuery()
				.processInstanceId(pi.getId()).list();
		Map<String, String> map = new HashMap<String, String>();
		for (Execution execution : excutionList) {
			if (execution.getActivityId() != null)
				map.put(execution.getActivityId(), execution.getActivityId());
		}
		List<String> excutionActivitId = new ArrayList<String>(map.values());
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(pi.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities();
		// 遍历所有节点找到当前节点的下一个节点
		for (ActivityImpl activityDef : activitiList) {
			for (String ActivitId : excutionActivitId) {
				if (ActivitId.equals(activityDef.getId())) {
					return nextActivityImpl(activityDef);
				}
			}
		}
		return null;
	}

	@Override
	public Boolean isJointTask(TaskDefinition taskDef)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object taskFormHandler = taskDef.getTaskFormHandler();
		Map porpMap = PropertyUtils.describe(taskFormHandler);
		List<FormPropertyHandler> formPropertyHandlers = (List<FormPropertyHandler>) porpMap
				.get("formPropertyHandlers");
		if (formPropertyHandlers == null || formPropertyHandlers.size() == 0)
			return Boolean.FALSE;
		for (FormPropertyHandler formPropertyHandler : formPropertyHandlers) {
			if ("taskType".equals(formPropertyHandler.getId())
					&& "会审".equals(formPropertyHandler.getName())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;

	}

	@Override
	public Boolean isAutoSetAssignments(TaskDefinition taskDef)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object taskFormHandler = taskDef.getTaskFormHandler();
		Map porpMap = PropertyUtils.describe(taskFormHandler);
		List<FormPropertyHandler> formPropertyHandlers = (List<FormPropertyHandler>) porpMap
				.get("formPropertyHandlers");
		if (formPropertyHandlers == null || formPropertyHandlers.size() == 0)
			return Boolean.FALSE;
		for (FormPropertyHandler formPropertyHandler : formPropertyHandlers) {
			if ("autoSetAssignments".equals(formPropertyHandler.getId())
					&& "0".equals(formPropertyHandler.getName())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}


	@Override
	public void restoreOutTransition(ActivityImpl activityImpl,
			List<PvmTransition> transitions) {
		// 清空现有出线
		activityImpl.getOutgoingTransitions().clear();
		// 恢复现有出线
		for (PvmTransition t : transitions) {
			activityImpl.getOutgoingTransitions().add(t);
		}
	}
	
	
	@Override
	public List<PvmTransition> clearOutTransition(ActivityImpl activityImpl) {
		List<PvmTransition> oriTransitionList = new ArrayList<PvmTransition>();
		// 备份现有出线
		for (PvmTransition t : activityImpl.getOutgoingTransitions()) {
			oriTransitionList.add(t);
		}
		activityImpl.getOutgoingTransitions().clear();

		return oriTransitionList;
	}
	
	
	@Override
	public Boolean isReuseOperation(TaskDefinition taskDef) throws IllegalAccessException, InvocationTargetException,
	NoSuchMethodException  {
		Object taskFormHandler = taskDef.getTaskFormHandler();
		Map porpMap = PropertyUtils.describe(taskFormHandler);
		List<FormPropertyHandler> formPropertyHandlers = (List<FormPropertyHandler>) porpMap
				.get("formPropertyHandlers");
		if (formPropertyHandlers == null || formPropertyHandlers.size() == 0)
			return Boolean.FALSE;
		for (FormPropertyHandler formPropertyHandler : formPropertyHandlers) {
			if ("ReuseOperation".equals(formPropertyHandler.getId())
					&& "0".equals(formPropertyHandler.getName())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean isJointByGruop(TaskDefinition taskDef)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object taskFormHandler = taskDef.getTaskFormHandler();
		Map porpMap = PropertyUtils.describe(taskFormHandler);
		List<FormPropertyHandler> formPropertyHandlers = (List<FormPropertyHandler>) porpMap
				.get("formPropertyHandlers");
		if (formPropertyHandlers == null || formPropertyHandlers.size() == 0)
			return Boolean.FALSE;
		for (FormPropertyHandler formPropertyHandler : formPropertyHandlers) {
			if ("jointByGroup".equals(formPropertyHandler.getId())
					&& "0".equals(formPropertyHandler.getName())) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	
	/**
	 * 该服务的私有方法 根据当前节点的底层定义对象，获取所有 当前节点的出线和出线终点，并返回封装后的集合 开发时间：2013-11-29
	 * 
	 * @param currentTaskDef
	 *            当前节点的底层定义
	 * @return 节点的出线和出线终点的集合，出线作为key，终点定义对象作为value
	 */
	public Map<PvmTransition, ActivityImpl> nextActivityImpl(
			ActivityImpl currentTaskDef) {
		Map<PvmTransition, ActivityImpl> result = new HashMap<PvmTransition, ActivityImpl>();
		List<PvmTransition> outTransitions = currentTaskDef
				.getOutgoingTransitions();
		for (PvmTransition tr : outTransitions) {
			PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
			String type = ac.getProperty("type").toString();
			// 过滤掉回退的线,只保留向前流转的线
			Object pvmTransitionType = tr.getProperty("conditionText");
			Object pvmTransitionName = tr.getProperty("name");
			if (type.equals("userTask")) {
				ActivityImpl nextTaskImpl = (ActivityImpl) ac;
				result.put(tr, nextTaskImpl);
			}
		}
		return result;
	}

	private ProcessInstance getProcessInstancsByBizKey(String bizKey) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		return pi;
	}

	@Override
	public Boolean isCallActivityElement(ActivityImpl activityImpl) {

		
		return null;
	}

	public Boolean isCountersign(String taskId) {
		TaskFormData fd = formService.getTaskFormData(taskId);
		List<FormProperty> fps = fd.getFormProperties();
		for (FormProperty fp : fps) {
			if ("taskType".equals(fp.getId())) {
				if (TaskType.会审.toString().equals(fp.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	

}
