package org.bachelor.bpm.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.bachelor.bpm.domain.ReviewResult;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.vo.PiStatus;
import org.bachelor.core.entity.IBaseEntity;

/**
 * 工作流运行时服务
 * 
 * @author user
 * 
 */
public interface IBpmRuntimeService {

	/**
	 * 启动流程
	 * 
	 * @param pdkey
	 *            流程定义Key
	 * @param bpInfoEx
	 *            流程和业务信息交换对象
	 * @return 流程实例
	 */
	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpInfoEx);

	public ProcessInstance startProcessInstanceByKey(String pdkey,
			BaseBpDataEx bpDataEx, IBaseEntity user);
	/**
	 * 取得当前人工节点的待选人列表
	 * 
	 * @return 当前人工节点的待选人列表
	 */
//	public List<? extends IBaseEntity> getTaskCandidateUser();

	/**
	 * 取得指定人工节点的待选人列表
	 * 
	 * @param piId
	 *            流程实例Id
	 * @param taskDefKey
	 *            人工节点定义Key
	 * @return 待选人列表
	 */
//	public List<? extends IBaseEntity> getTaskCandidateUserByDefKey(String piId,
//			String taskDefKey);

	/**
	 * 取得指定人工节点的待选人列表 此方法从流程变量中解析，而不再使用BaseBpDataEx
	 * 
	 * @param piId
	 *            流程实例Id
	 * @param taskDefKey
	 *            人工节点定义Key
	 * @return 待选人列表
	 */
//	public List<? extends IBaseEntity> getTaskCandidateUserByDefKey2(String piId,
//			String taskDefKey);

	/*
	 * 
	 * 查询历史流程节点待办人信息
	 * 
	 * pdid 流程定义ID piId 流程实例ID taskDefKey 节点定义ID
	 */
//	public List<? extends IBaseEntity> getHisTaskCandidateUserByDefKey(String pdid, String PiId,
//			String taskDefKey);

	/**
	 * 完成节点并进行流程流转
	 * 
	 * @param taskId
	 *            节点Id
	 */
	public <T extends BaseBpDataEx> void complete(String taskId, String userId, T bpDataEx);

	/**
	 * 完成节点并进行流程流转,并且由指定用户接管下个节点
	 * 
	 * @param taskId
	 *            节点Id
	 * @param userId
	 *            用户id
	 * 
	 */
	public <T extends BaseBpDataEx>void completeAndClaim(String taskId, String userId,T bpDataEx);

	/**
	 * 完成当前节点并进行流程流转
	 * 
	 * 使用方法： public void complete(String taskId);
	 */
	@Deprecated
	public void complete();

	/**
	 * 完成当前节点并进行流程流转,并且由指定用户接管下个节点
	 * 
	 * @param userId
	 *            用户id
	 * 
	 */
	public void completeAndClaim(String userId);

	/**
	 * 取得BpInfoEx对象
	 * 
	 * @param piId
	 *            流程实例Id
	 * @param userId  可以传空
	 * 
	 */
	public BaseBpDataEx getBpDataEx(String piId, String userId);

	/**
	 * 取得BpInfoEx对象
	 * 
	 * @param piId
	 *            TaskId
	 * @param userId 可以为空
	 */
	public BaseBpDataEx getBpDataExByTaskId(String taskId, String userId);

	/**
	 * 为节点分派代理人
	 * 
	 * @param taskId
	 * @param userId
	 */
	public void setAssignee(String taskId, String userId);

	/**
	 * 根据流程实例id删除对应流程
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 * @param deleteReason
	 *            删除原因
	 */
	public void deleteProcessInstance(String processInstanceId,
			String deleteReason);

	/**
	 * 为指定节点添加候选人
	 * 
	 * @param taskId
	 *            节点id
	 * @param userId
	 *            用户id
	 * @param userId
	 *            用户 组id
	 */
	public void addCandidateUser(String taskId, List<String> userId, List<String> groupId);

	/**
	 * 根据业务数据对象id,取得BpInfoEx对象
	 * 
	 * @param bizKey
	 * @return
	 */
	public BaseBpDataEx getBpDataExByBizKey(String bizKey, String userId);

	/**
	 * 根据bizKey，查询当前节点的候选人
	 * 
	 * @param bizKey
	 * @return 候选人用户集合
	 */
//	public List<? extends IBaseEntity> getTaskCandidateUserByBizKey(String bizKey, String userId);

	/**
	 * 获取指定流程和指定节点的注释信息
	 * 
	 * @param taskId
	 * @param processInstanceId
	 * @param message
	 * @return 注释信息
	 */
	public Comment getTaskComment(String taskId);

	/**
	 * 获取指定的流程实例所有未执行节点的对象集合
	 * 
	 * @param piid
	 * @return 未执行节点的对象集合
	 */
	public List<TaskEx> getUnFinishedTaskByPiid(String piid);

	/**
	 * 流转当前节点并记录审核信息。 只能在当前节点是审核节点时调用此方法。 此方法会进行节点流转并记录审核信息。
	 * 
	 * 新增修改：添加对流转目标节点信息的记录 修改人：张涛 修改时间：2013.11.17
	 * 
	 * @param taskId
	 *            审核节点Id
	 * @param title
	 *            审核标题
	 * @param content
	 *            审核内容
	 * @param comment
	 *            审核备注
	 * @param result
	 *            审核结果
	 * @param force
	 *            是否不验证用户强制提交
	 * 
	 * @return 审核信息实体类
	 */
	public <T extends BaseBpDataEx>BpmTaskReview completeReview(String taskId, String userId,
			String title, String content, String comment, String fallBackReason,
			ReviewResult result, String[] taskCandidate,T bpDataEx, boolean force);
	
	public <T extends BaseBpDataEx>BpmTaskReview completeReview(String taskId, String id,
			String comment, ReviewResult result, String string,
			T bpData, boolean force);
	
	public BpmTaskReview completeReview(ProcessInstance pi, String userId, String comment,
			ReviewResult result, String taskCandidate);

	/**
	 * 不论当前用户是否有权限，强制提交流程
	 * @param pi
	 * @param userId
	 * @param comment
	 * @param result
	 * @param taskCandidate
	 * @return
	 */
	public BpmTaskReview completeReviewForce(ProcessInstance pi, String userId,
			String comment, ReviewResult result, String taskCandidate);
	
	/**
	 * 查询指定节点相关的审核信息。 此节点必须是审核节点。 审核信息按照审核时间顺序排列。
	 * 
	 * @param taskId
	 *            审核节点ID
	 * @return 节点相关的审核信息
	 */
	public List<BpmTaskReview> getTaskReviewsByTaskId(String taskId);

	/**
	 * 查询指定业务Key相关的审核信息。 如果流程中有多个审核节点，那么审核节点的审核信息将全部返回。 审核信息按照审核时间顺序排列。
	 * 
	 * 
	 * @param bizKey
	 *            业务Key
	 * @return 业务Key相关的审核信息
	 */
	public List<BpmTaskReview> getTaskReviewsByBizKey(String bizKey);

	/**
	 * 设置流程变量
	 * 
	 * @param piId
	 *            流程实例id
	 * @param variableName
	 *            参数名称
	 * @param variableValue
	 *            参数值
	 */
	public void setPiVariable(String piId, String variableName,
			String variableValue);

	/**
	 * 取得流程变量
	 * 
	 * @param piId
	 *            流程实例id
	 * @param variableName
	 *            参数名称
	 * @return 指定参数名称对应的值
	 */
	public Object getPiVariable(String piId, String variableName);

	/**
	 * 取得指定BizKey对应的流程状态
	 * 
	 * @param bizKey
	 *            BizKey
	 * @return 流程状态详细信息
	 */
	public PiStatus getPiStatusByBizKey(String bizKey);

	/**
	 * 取得流程变量Map
	 * 
	 * @param piId
	 *            流程实例id
	 * @return 流程变量Map
	 */
	Map<String, Object> getPiVariableMap(String piId);

	/**
	 * 查找指定的流程实例
	 * 
	 * @param piId
	 *            流程实例Id
	 * @return 流程实例
	 */
	public ProcessInstance findByPiId(String piId);

	/**
	 * 查找BizKey对应的流程实例
	 * 
	 * @param bizKey
	 *            BizKey
	 * @return 流程实例
	 */
	public ProcessInstance findByBizKey(String bizKey);

	/**
	 * 查找piId对应的流程实例
	 * 
	 * @param piId
	 *            piId
	 * @return 流程实例
	 */
	public PiStatus getPiStatusByPiId(String piId);

	/**
	 * 流转流程到指定人工节点
	 * 
	 * @param taskId
	 *            当前节点
	 * @param targetTaskDefKey
	 *            流转目标人工节点
	 */
	public <T extends BaseBpDataEx> void signal(String taskId, String userId, String targetTaskDefKey,T bpDataEx);

	/**
	 * 根据业务key，获取流程当前节点的 所有出线节点的代办人列表
	 * 
	 * @param bizKey
	 * @return 所有出线节点的代办人集合
	 */
//	public List<? extends IBaseEntity> getNextTaskCandidateUser(String bizKey);



	/**
	 * 根据节点定义，获取该节点的代办人集合
	 * 
	 * @param taskDef
	 * @return 节点的代办人集合
	 */
//	public List<? extends IBaseEntity> getNextTaskCandidateUser(String bizKey,
//			String outGoingTransValue, BaseBpDataEx bpDataEx);

	/**
	 * 根据业务key，获取流程当前活动节点的全部出线节点定义
	 * 
	 * @param bizKey
	 *            业务key
	 * @return 出线节点的定义集合
	 */
	public Map<PvmTransition, TaskDefinition> getNextTaskDefinition(
			String bizKey);

	/**
	 * 将BpDataEx中的数据，与流程变量合并
	 * @param bpDataEx
	 * @return 流程变量的Map
	 */
	public Map<String, Object> mergeProcessVaribleMap(BaseBpDataEx bpDataEx);

	public List<BpmTaskReview> getWaitingTasks(String id);

	public List<BpmTaskReview> getFinishedTasks(String id);
	
}
