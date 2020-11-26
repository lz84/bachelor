package org.bachelor.bpm.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程图形数据VO
 * @author user
 *
 */
public class GraphicData {

	/** POP标题 **/
	private String title;
	/** ID **/
	private String id;
	/** 责任者或者负责人 **/
	private String owner;
	/** 责任者或者负责人名称 **/
	private String ownerName;
	/** 执行人 **/
	private String assignee;
	/**执行人名称**/
	private String assigneeName;
	/**执行单位**/
	private String assigneeUnit;
	/** 开始时间 **/
	private String startTime;
	/** 结束时间**/
	private String endTime;
	/** 候选人 **/
	private String candidate;
	/** 候选人名称 **/
	private String candidateName;
	/** 候选人角色信息**/
	private String candidateRoles;
	/** 类型 **/
	private String type;
	/** 已执行数据集合 **/
	private List<GraphicData> gds = new ArrayList<GraphicData>();
	 
	public String getAssigneeUnit() {
		return assigneeUnit;
	}
	public void setAssigneeUnit(String assigneeUnit) {
		this.assigneeUnit = assigneeUnit;
	}
	public String getCandidateRoles() {
		return candidateRoles;
	}
	public void setCandidateRoles(String candidateRoles) {
		this.candidateRoles = candidateRoles;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCandidate() {
		return candidate;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<GraphicData> getGds() {
		return gds;
	}
	public void setGds(List<GraphicData> gds) {
		this.gds = gds;
	}
}
