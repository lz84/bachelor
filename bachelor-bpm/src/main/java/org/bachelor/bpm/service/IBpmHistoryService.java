package org.bachelor.bpm.service;

import java.util.Date;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;

import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpmTaskReview;

/**
 * 历史流程数据查询类
 * 
 * @author zhangtao
 * 
 */
public interface IBpmHistoryService {

	/**
	 * 根据流程定义名称查询历史流程
	 * 
	 * @param pdName
	 *            流程定义id
	 * @return 查询到的流程定义对象
	 */
	public List<ProcessDefinition> getProcessByDefinitionId(String pdName);

	/**
	 * 根据业务domain的ID查询历史流程
	 * 
	 * @param domainId
	 * @return 查询到的流程对象
	 */
	public HistoricProcessInstance getProcessByDomainId(String domainId);

	/**
	 * 根据用户id查询（用户类型包括候选者和执行者）历史流程
	 * 
	 * @param userId
	 * @return 查询到的流程对象
	 */
	public List<ProcessDefinition> getProcessByUser(String userId);

	/**
	 * 根据流程的启动时间和完成时间查询历史流程
	 * 
	 * @param startTime
	 * @param endTime
	 * @return 查询到的流程对象
	 */
	public List<ProcessDefinition> getProcessByTime(String startTime,
			String endTime);

	/**
	 * 查询历史流程
	 * 
	 * @param pdId
	 *            流程定义id
	 * @param domainId
	 *            业务domain的ID
	 * @param userId
	 *            用户id（用户类型包括候选者和执行者）
	 * @param startTime
	 *            启动时间
	 * @param endTime
	 *            完成时间
	 * @return 流程实例
	 */
	public List<HistoricProcessInstance> getProcessInstance(String pdId,
			String domainId, String userId, String startTime, String endTime);

	/**
	 * 根据指定的条件查询所有已结束流程实例
	 * 
	 * @param processId
	 *            流程id
	 * @param taskId
	 *            节点id
	 * @param startedOrgId
	 *            发起公司
	 * @param startTime
	 *            发起时间（前区间）
	 * @param endTime
	 *            发起时间（后区间）
	 * @return 流程实例集合
	 */
	public List<HistoricProcessInstance> getFinishedProcessInstance(
			String processDefinitionId, String taskKey, String startedOrgId,
			Date startTime, Date endTime);
	/**
	 * 根据指定的条件查询所有未结束流程实例
	 * 
	 * @param processId
	 *            流程id
	 * @param taskId
	 *            节点id
	 * @param startedOrgId
	 *            发起公司
	 * @param startTime
	 *            发起时间（前区间）
	 * @param endTime
	 *            发起时间（后区间）
	 * @return 流程实例集合
	 */
	@Deprecated
	public List<HistoricProcessInstance> getUnfinishedProcessInstance(
			String processDefinitionId, String taskKey, String startedOrgId,
			String startTime, String endTime);

	
	/**
	 * 根据给定的bizKey获取对应的流程的历史数据
	 * @param bizKey 业务实体key
	 * @return 流程节点执行历史
	 */
	public List<HistoricTaskInstance> getProcessHistoricByBizKey(String bizKey);
	
	
	/**
	 * 根据业务Key取得已结束流程对应的BpDataEx
	 * @param bizKey 业务Key
	 * @return BpDataEx
	 */
	public BaseBpDataEx getBpDataExByBizKey(String bizKey);
	
	/**
	 * 查找指定的流程实例
	 * 
	 * @param piId 流程实例Id
	 * @return 流程实例
	 */
	public HistoricProcessInstance findByPiId(String piId);
	
	/**
	 * 查找BizKey对应的流程实例
	 * @param bizKey BizKey
	 * @return 流程实例
	 */
	public HistoricProcessInstance findByBizKey(String bizKey);

	/**
	 * 取得指定的流程变量
	 * 
	 * @param piId 流程实例Id
	 * @param varName 流程变量名
	 * @return 流程变量值。没有找到返回null
	 */
	public Object getPiVariable(String piId, String varName);
	
	/**
	 * 功能:删除历史流程
	 * 作者 曾强 2013-8-22下午4:42:45
	 * @param processInstanceId 流程实例ID
	 */
	public void deleteHistoricProcessInstance(String processInstanceId);

}
