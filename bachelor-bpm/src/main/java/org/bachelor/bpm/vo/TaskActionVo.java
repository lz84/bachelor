package org.bachelor.bpm.vo;

import java.util.Date;
import java.util.List;

import org.bachelor.core.entity.IBaseEntity;

public class TaskActionVo {

	private IBaseEntity operator;
	private IBaseEntity operCompany;
	private Date startDate;
	private Date completeDate;
	private List<String> comments;
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public IBaseEntity getOperator() {
		return operator;
	}
	public void setOperator(IBaseEntity operator) {
		this.operator = operator;
	}
	public IBaseEntity getOperCompany() {
		return operCompany;
	}
	public void setOperCompany(IBaseEntity operCompany) {
		this.operCompany = operCompany;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
}
