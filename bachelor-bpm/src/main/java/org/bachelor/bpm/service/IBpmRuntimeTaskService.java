package org.bachelor.bpm.service;

import java.util.List;

import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.domain.TaskType;
import org.bachelor.core.entity.IBaseEntity;

/**
 * 节点查询服务类
 * 
 * @author zhangtao
 * 
 */
public interface IBpmRuntimeTaskService {
	public List<TaskEx> getAllActiveTask(String taskDefKey);

	public List<TaskEx> getTaskByCandidateUser(String userId);

	public List<TaskEx> getTaskByCandidateRole(String roleId);

	public List<TaskEx> getTaskByAssignee(String userId);

	public List<TaskEx> getTaskByCandidateUser(String pdkey, String userId);

	public List<TaskEx> getTaskByCandidateRole(String pdkey, String roleId);

	public List<TaskEx> getTaskByAssignee(String pdkey, String userId);

	public TaskEx getTask(String taskId, String userId, String assignmentType);

	public String getTaskURL(Task task);
	
	/**
	 * 为业务流程或是流程节点添加说明
	 * 
	 * @param taskId
	 * @param processInstanceId
	 * @param message
	 * @return 返回封装业务流程或是流程节点的对象
	 */
	public Comment addComment(String taskId, String processInstanceId,
			String message);

	/**
	 * 获取指定id的节点的全部候选人
	 * 
	 * @param taskId
	 * @return 指定节点的全部候选人
	 */
//	public List<? extends IBaseEntity> getUsersByTaskId(String taskId);
    
	/**
     * 获取指定节点定义的全部候选人
     * @param taskDefinition 节点定义对象
     * @return 指定节点定义的全部候选人
     */
//	public List<? extends IBaseEntity> getUserByTaskDefinition(TaskDefinition taskDefinition,String processInstanceId);
	
    /**
     * 获取指定节点定义的全部候选人
     * 此方式使用流程变量，不再使用BaseBpDataEx
     * @param taskDefinition 节点定义对象
     * @return 指定节点定义的全部候选人
     */
//	public List<IBaseEntity> getUserByTaskDefinition2(TaskDefinition taskDefinition,String processInstanceId);

    /**
     * 获取指定节点定义的候选角色
     * 返回指格式：角色描述#单位名称
     * 
     * @param taskDefinition 节点定义对象
     * @return 指定节点定义的候选角色
     */
//	public List<String> getRoleByTaskDefinition(TaskDefinition taskDefinition,String processInstanceId);
	
	/**
	 * 为指定节点设置参数
	 * @param executionId 执行中的节点id
	 * @param variableName  参数名称
	 * @param variableValue 参数值
	 */
	public void setTaskVariable(String executionId,String variableName,String variableValue);

	public TaskEx getTask(String taskId);
	
	/**
	 * 获取指定流程实例的活动节点
	 * @param piId 流程实例Id
	 * @return 活动节点
	 */
	public List<TaskEx> getActiveTask(String piId);
	public List<TaskEx> getActiveTask(String piId, String userId);

	/**
	 * 查询指定Id的人工节点定义
	 * @param pdId 流程定义Id
	 * @param taskDefKey 人工节点定义
	 * @return 人工节点定义
	 */
	TaskDefinition getTaskDefition(String pdId, String taskDefKey);
	
	/**
	 * 查询指定Id节点的类型
	 * 
	 * @param taskId 节点Id
	 * @return 节点类型
	 */
	TaskType getTaskType(String taskId);

	PvmActivity getFirstTask(String pdId);
	
	/**
	 * 根据当前节点候选人和流程定义Key查询BaseBpDataEx
	 * 查询Task排序原则，对Task创建时间正序排序，对Task名称正序排序
	 * 修改人：张涛
	 * 修改时间：2013/11/12
	 * 修改内容：修复了根据权限查询task的逻辑问题
	 * @param pdkey 流程定义Key
	 * @param userId 当前节点的候选人 
	 * @return BaseBpDataEx的集合
	 */
//	public List<BaseBpDataEx> getBpDataExByCandidateUser(String pdkey,
//			String candidateUserId);
	
	/**
	 * 根据当前节点受理人和流程定义Key查询BaseBpDataEx
	 * 查询Task排序原则，对Task创建时间正序排序，对Task名称正序排序
	 * @param pdkey 流程定义Key
	 * @param userId 当前节点的候选人 
	 * @return BaseBpDataEx的集合
	 */
	public List<BaseBpDataEx> getBpDataExByAssignee(String pdkey,String assignee);
	
	/**
	 *  获取指定流程的第一个用户节点
	 * @param   proceeDef
	 * @return  
	 */
	public List<TaskDefinition> getFirstTaskDef(String bizKey);
	
    /**
     * 获取指定流程的最后一个用户节点
     * @param proceeDef
     * @return
     */
	public List<TaskDefinition> getLastTaskDef(String bizKey);

	

	
	
	
}
