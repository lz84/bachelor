package org.bachelor.bpm.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bachelor.bpm.vo.GraphicData;

public class ProcessInstanceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641754514969053186L;

	private String Id;
	
	/** 流程定义ID**/
	private String processDefinitionId;
	/** 流程实例ID**/
	private String processInstanceId;
	/** 流程活动ID**/
	private String activityId;
	/** 流程业务ID **/
	private String bizKey;
	/** 待办人OA**/
	private String assignee;
	/** 待办人 **/
	private String assigneeName;
	/** 待办人单位 **/
	private String assigneeUnit;
	/** 开始时间 **/
	private String stratTime;
	/** 结束时间 **/
	private String endTime;
	/** 发起单位ID **/
	private String startOrgId;
	/** 发起单位名称 **/
	private String startorgName;
	/** 发起人 **/
	private String startUserName;
	/** 发起时间 **/
	private String startDate;
	/** 流程名称**/
	private String processName;
	/** 保存类型 **/
	private String saveType;
	/** 删除信息理由 **/
	private String reason;
	/** 节点名称**/
	private String taskName;
	/** 节点ID **/
	private String taskId;
	
	/** 节点详细信息 **/
	private List<GraphicData> gds;
	
	/** 流程执行数据 **/ 
	private Map<String,Object> processDatas;

	public Map<String, Object> getProcessDatas() {
		return processDatas;
	}
	public void setProcessDatas(Map<String, Object> processDatas) {
		this.processDatas = processDatas;
	}
	public String getAssigneeUnit() {
		return assigneeUnit;
	}
	public void setAssigneeUnit(String assigneeUnit) {
		this.assigneeUnit = assigneeUnit;
	}
	public List<GraphicData> getGds() {
		return gds;
	}
	public void setGds(List<GraphicData> gds) {
		this.gds = gds;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getStartUserName() {
		return startUserName;
	}
	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getStratTime() {
		return stratTime;
	}
	public void setStratTime(String stratTime) {
		this.stratTime = stratTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	 
	public String getBizKey() {
		return bizKey;
	}
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public String getStartOrgId() {
		return startOrgId;
	}
	public void setStartOrgId(String startOrgId) {
		this.startOrgId = startOrgId;
	}
	public String getStartorgName() {
		return startorgName;
	}
	public void setStartorgName(String startorgName) {
		this.startorgName = startorgName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
}
