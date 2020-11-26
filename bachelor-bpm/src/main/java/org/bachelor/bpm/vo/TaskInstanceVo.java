package org.bachelor.bpm.vo;

import java.util.List;

import org.bachelor.core.entity.IBaseEntity;

public class TaskInstanceVo {

	private String pdid;
	private String piid;
	private String executionId;
	private String id;
	private String defKey;
	private String name;
	private String desciption;
	private int priority;
	private IBaseEntity owner;
	private IBaseEntity assignee;
	private List<IBaseEntity> assignments;
	public IBaseEntity getAssignee() {
		return assignee;
	}
	public void setAssignee(IBaseEntity assignee) {
		this.assignee = assignee;
	}
	public List<IBaseEntity> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<IBaseEntity> assignments) {
		this.assignments = assignments;
	}
	private List<IBaseEntity> assignmentRole;
	private String status;
	private List<TaskActionVo> taskActions;
	
	public String getPdid() {
		return pdid;
	}
	public void setPdid(String pdid) {
		this.pdid = pdid;
	}
	public String getPiid() {
		return piid;
	}
	public void setPiid(String piid) {
		this.piid = piid;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDefKey() {
		return defKey;
	}
	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesciption() {
		return desciption;
	}
	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public IBaseEntity getOwner() {
		return owner;
	}
	public void setOwner(IBaseEntity owner) {
		this.owner = owner;
	}
	public List<IBaseEntity> getAssignmentRole() {
		return assignmentRole;
	}
	public void setAssignmentRole(List<IBaseEntity> assignmentRole) {
		this.assignmentRole = assignmentRole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TaskActionVo> getTaskActions() {
		return taskActions;
	}
	public void setTaskActions(List<TaskActionVo> taskActions) {
		this.taskActions = taskActions;
	}
	
}
