package org.bachelor.bpm.domain;

import org.activiti.engine.task.Task;

public class TaskEx {

	/**	对应的Activiti中的Task实体 **/
	private Task task;
	/**	节点对应的FormKey **/
	private String formKey;
	/**	节点类型 **/
	private TaskType taskType;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public TaskType getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	
	
}
