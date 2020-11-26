package org.bachelor.bpm.service;

import java.util.Date;
import java.util.List;

import org.activiti.engine.task.Task;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 流程仓库服务类
 * 
 * @author user
 * 
 */
public interface IBpmRepositoryService {

	/**
	 * 查询所有流程。 排序规则：流程名称顺序，流程版本逆序
	 * 
	 * @return 查询到的所有流程
	 */
	List<ProcessDefinition> getAllPd();


	/**
	 * 查询所有最新流程。 排序规则：流程名称顺序，流程版本逆序
	 * 
	 * @return 查询到的所有流程
	 */
	List<ProcessDefinition> getAllLastedPd();

	/**
	 * 取得指定流程的所有人工节点
	 * 
	 * @param pdId
	 *            流程ID
	 * @return 流程的所有人工节点
	 */
	List<Task> getAllTask(String pdId);

	/**
	 * 根据指定的条件查询流程实例
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
	public List<HistoricProcessInstance> queryProcess(String processDefinitionId, String taskDefinitionKey,
			String startedOrgId, Date startTime, Date endTime);

	/**
	 * 取得指定流程定义Id的流程图
	 * 
	 * @param pdId
	 *            流程定义Id
	 * @return 流程图的二进制数组
	 */
	public byte[] getProcessDiagram(String pdId);
	/**
	 * 根据流程定义id，获取所有节点的对象
	 * @param bizKey 流程业务key
	 * @return 节点定义的集合
	 */
    public List<TaskDefinition> getAllTaskDefinition(String bizKey);
    
    /**
     * 根据流程定义id取得流程图节点图形信息
     * @param pdId 流程定义Id
     * @return 流程图节点图形信息
     */
    public DiagramLayout getProcessDiagramLayout(String pdId);
    
    
	
	/**
	 * 根据流程定义Id查询流程定义
	 * 
	 * @return 流程定义
	 */
	public ProcessDefinition getPdById(String pdId);
   
	/**
	 * 根据流程业务key，获取该流程的所有节点定义（只有运行中流程可用）
	 * @param piid 流程实例id
	 * @return
	 */
    public List<ActivityImpl> getProcessActivitImpl(String bizKey);
    
    
}
