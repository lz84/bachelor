package org.bachelor.bpm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.NativeHistoricVariableInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.bachelor.bpm.common.BpmConstant;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.bachelor.bpm.service.IBpmHistoryService;

@Service
public class BpmHistoryServiceImpl implements IBpmHistoryService {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	/**
	 * 流程历史查询方式有： 1.根据流程定义名称查询 2.根据domain的ID查询 3.根据用户查询。用户类型包括候选者和执行者
	 * 4.根据流程的启动时间和完成时间
	 * 
	 * 流程历史信息查询结果包括： 1. 流程定义信息 2. 流程人工节点信息 3. 各节点执行的信息：执行人，执行时间，comment
	 */

	@Override
	public List<ProcessDefinition> getProcessByDefinitionId(String pdName) {
		return null;
	}

	@Override
	public HistoricProcessInstance getProcessByDomainId(String domainId) {
		List<HistoricProcessInstance> hpi = historyService
				.createHistoricProcessInstanceQuery().finished()
				.processInstanceBusinessKey(domainId).list();
		if (hpi == null || hpi.size() == 0) {
			return null;
		}
		return hpi.get(0);
	}

	@Override
	public List<ProcessDefinition> getProcessByUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProcessDefinition> getProcessByTime(String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HistoricProcessInstance> getProcessInstance(String pdId,
			String domainId, String userId, String startTime, String endTime) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery().finished();

		if (StringUtils.isEmpty(pdId)) {
			query.processInstanceId(pdId);
		}
		if (StringUtils.isEmpty(domainId)) {
			query.processInstanceBusinessKey(domainId);
		}
		if (StringUtils.isEmpty(userId)) {
			query.involvedUser(userId);
		}
		if (StringUtils.isEmpty(startTime)) {
			query.startedAfter(new Date(startTime));
		}
		if (StringUtils.isEmpty(endTime)) {
			query.finishedBefore(new Date(endTime));
		}
		List<HistoricProcessInstance> hpi = query.list();
		return hpi;
	}

	@Override
	public List<HistoricProcessInstance> getFinishedProcessInstance(
			String processDefinitionId, String taskKey, String startedOrgId,
			Date startTime, Date endTime) {
		List<HistoricProcessInstance> hpiList = new ArrayList<HistoricProcessInstance>();
		HistoricProcessInstanceQuery hipQuery = historyService
				.createHistoricProcessInstanceQuery().finished();
		if (!StringUtils.isEmpty(processDefinitionId)) {
			hipQuery.processDefinitionId(processDefinitionId);
		}
		if (startTime != null) {
			hipQuery.startedAfter(startTime);
		}
		if (endTime != null) {
			hipQuery.startedBefore(endTime);
		}
		if(!StringUtils.isEmpty(startedOrgId)){
			hipQuery.variableValueEquals("startCompanyId", startedOrgId);
		}
		hpiList = hipQuery.includeProcessVariables().list();
		/*for (HistoricProcessInstance hpi : hpiList) {
			BaseBpDataEx bpDataEx = (BaseBpDataEx) historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(hpi.getId())
					.variableName(Constant.BPM_BP_DATA_EX_KEY).singleResult()
					.getValue();
			bpDataEx.getPiId();
		}*/
		return hpiList;
	}

	@Override
	public List<HistoricProcessInstance> getUnfinishedProcessInstance(
			String processDefinitionId, String taskKey, String startedOrgId,
			String startTime, String endTime) {
		List<HistoricProcessInstance> hpiList = new ArrayList<HistoricProcessInstance>();
		HistoricProcessInstanceQuery hipQuery = historyService
				.createHistoricProcessInstanceQuery().finished()
				.orderByProcessDefinitionId().desc();
		if (processDefinitionId != null) {
			hipQuery.processDefinitionId(processDefinitionId);
		}
		if (startTime != null) {
			hipQuery.startedAfter(new Date(startTime));
		}
		if (endTime != null) {
			hipQuery.startedBefore(new Date(endTime));
		}
		hpiList = hipQuery.includeProcessVariables().list();
		for (HistoricProcessInstance hpi : hpiList) {
			BaseBpDataEx bpDataEx = (BaseBpDataEx) historyService
					.createHistoricVariableInstanceQuery()
					.processInstanceId(hpi.getId())
					.variableName(BpmConstant.BPM_BP_DATA_EX_KEY).singleResult()
					.getValue();
			bpDataEx.getPiId();
		}

		return null;
	}

	@Override
	public List<HistoricTaskInstance> getProcessHistoricByBizKey(String bizKey) {
		//判断对应的bizKey是否有进行中流程实例
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		List<HistoricTaskInstance> historicTaskList = new ArrayList<HistoricTaskInstance>();
		if (pi != null) {
			//如果不为空
			historicTaskList = this.getUnFinishedProcessHistoricTask(pi
					.getProcessInstanceId());
		} else {
			//没有流程实例与bizKey对应，则视为该流程是已结束的流程
			historicTaskList = this
					.getFinishedProcessHistoricTaskByBizKey(bizKey);
		}
        if(historicTaskList==null||historicTaskList.size()==0){
        	return null;
        }
		return historicTaskList;
	}

	private List<HistoricTaskInstance> getUnFinishedProcessHistoricTask(
			String executionId) {
		List<HistoricTaskInstance> historicTaskList = new ArrayList<HistoricTaskInstance>();
		historicTaskList = historyService.createHistoricTaskInstanceQuery()
				.executionId(executionId).finished().processUnfinished().orderByTaskDueDate().asc().list();
		return historicTaskList;

	}

	private List<HistoricTaskInstance> getFinishedProcessHistoricTaskByBizKey(
			String bizKey) {
		List<HistoricTaskInstance> historicTaskList = new ArrayList<HistoricTaskInstance>();
		historicTaskList = historyService.createHistoricTaskInstanceQuery()
				.finished().processFinished()
				.processInstanceBusinessKey(bizKey).orderByTaskDueDate().asc().list();
		return historicTaskList;

	}

	@Override
	public BaseBpDataEx getBpDataExByBizKey(String bizKey) {
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		if(hpi == null){
			return null;
		}
		HistoricVariableInstance obj = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(hpi.getId()).variableName(BpmConstant.BPM_BP_DATA_EX_KEY).singleResult();
			if(obj != null){
				BaseBpDataEx bpDataEx = (BaseBpDataEx)obj.getValue();
				bpDataEx.setPiId(hpi.getId());
				return bpDataEx;
			}
		
		return null;
	}

	@Override
	public HistoricProcessInstance findByPiId(String piId) {
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(piId).singleResult();
		return hpi;
	}

	@Override
	public HistoricProcessInstance findByBizKey(String bizKey) {
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		return hpi;
	}

	@Override
	public Object getPiVariable(String piId, String varName) {
		NativeHistoricVariableInstanceQuery q = historyService.createNativeHistoricVariableInstanceQuery()
					.sql("select * from ACT_HI_VARINST t where t.proc_inst_id_='" + piId + "' and name_='" + varName + "'" );
		if(q.singleResult() == null){
			return "";
		}
		Object value = q.singleResult().getValue();
		return value;
		
	}

	@Override
	public void deleteHistoricProcessInstance(String processInstanceId) {
		String piIds[] = processInstanceId.split(",");
		for(String id : piIds){
			if(StringUtils.isEmpty(id)){
					historyService.deleteHistoricProcessInstance(processInstanceId);
			}
		}
	}

}
