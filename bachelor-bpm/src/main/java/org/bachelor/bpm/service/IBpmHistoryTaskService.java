package org.bachelor.bpm.service;

import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.bachelor.bpm.domain.TaskEx;

public interface IBpmHistoryTaskService {
	/**
	 * 取得指定TaskId的上一个Task 只在顺序流程中返回值是有效的
	 * 
	 * @param taskId
	 * 
	 * @return 指定TaskId的上一个Task
	 */
	public TaskEx getPreTask(String taskId);

	/**
	 * 获取指定的流程实例的已执行结束的节点对象集合
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 * @return 该流程已执行结束的节点对象
	 */
	public List<HistoricTaskInstance> getFinishedTaskByPiid(String processInstanceId);

	

	/**
	 * 查询指定的Task实例对象。
	 * 
	 * 
	 * @param piid 流程实例Id
	 * @param taskDefKey Task定义Key
	 * @return Task实例
	 */
	public HistoricTaskInstance queryTaskInstanceVo(String piid, String taskDefKey);
	
	/**
	 * 取得指定Task的已完成实例。
	 * 排序规则：按节点的完成时间顺序排序
	 * 
	 * @param piid 流程实例Id
	 * @param taskDefKey Task定义Key
	 * @return Task的已完成实例
	 */
	public List<HistoricTaskInstance> getHistoricTasks(String piid, String taskDefKey);
	
	
	
	
}
