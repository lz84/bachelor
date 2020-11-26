package org.bachelor.bpm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程审核信息实体类
 * 
 * @author hubin
 * 
 */
@Entity
@Table(name = "t_ufp_bpm_task_review")
public class BpmTaskReview {

	/** 主键 **/
	@Id
	@GenericGenerator(name = "uuidGen", strategy = "uuid")
	@GeneratedValue(generator = "uuidGen")
	private String id;

	/** 流程定义Id **/
	@Column(name = "PD_ID")
	private String pdId;

	/** 流程定义key **/
	@Column(name = "PD_KEY")
	private String pdKey;

	/** 流程定义名称 **/
	@Column(name = "PD_NAME")
	private String pdName;

	/** 流程定义版本 **/
	@Column(name = "PD_VERSION")
	private int pdVersion;

	/** 流程实例ID **/
	@Column(name = "PI_ID")
	private String piId;

	/** 执行实例ID **/
	@Column(name = "EXEC_ID")
	private String execId;

	/** 业务关联Key **/
	@Column(name = "BIZ_KEY")
	private String bizKey;

	/** 审核节点ID **/
	@Column(name = "REVIEW_TASK_ID")
	private String reviewTaskId;

	/** 审核节点Key **/
	@Column(name = "REVIEW_TASK_DEF_KEY")
	private String reviewTaskDefKey;

	/** 审核节点名称 **/
	@Column(name = "REVIEW_TASK_NAME")
	private String reviewTaskName;

	/** 审核人ID **/
	@Column(name = "REVIEW_USER_ID")
	private String reviewUserId;

	/** 审核人名称 **/
	@Column(name = "REVIEW_USER_NAME")
	private String reviewUserName;

	/** 审核公司（单位）ID **/
	@Column(name = "REVIEW_COMPANY_ID")
	private String reviewCompanyId;

	/** 审核公司（单位）名称 **/
	@Column(name = "REVIEW_COMPANY_NAME")
	private String reviewCompanyName;

	/** 审核部门ID **/
	@Column(name = "REVIEW_DEPARTMENT_ID")
	private String reviewDepartmentId;

	/** 审核部门名称 **/
	@Column(name = "REVIEW_DEPARTMENT_NAME")
	private String reviewDepartmentName;

	/** 审核时间 **/
	//@JsonSerialize(using=DefaultDateSerialize.class) 
	@Column(name = "REVIEW_DATE")
	private Date reviewDate;

	/** 审核标题 **/
	@Column(name = "REVIEW_TITLE")
	private String reviewTitle;

	/** 审核内容 **/
	@Column(name = "REVIEW_CONTENT")
	private String reviewContent;

	/** 审核注释 **/
	@Column(name = "REVIEW_COMMENT")
	private String reviewComment;

	/** 审核结果.0：通过，1：驳回 **/
	@Column(name = "REVIEW_RESULT")
	private String reviewResult;

	/** 审核类型。1：审核，2：会审 **/
	@Column(name = "REVIEW_TYPE")
	private String reviewType;

	/** 流转的目标节点实例ID **/
	@Column(name = "TARGET_TASK_ID")
	private String targetTaskId;
	/** 流转的目标节点 定义ID **/
	@Column(name = "TARGET_TASK_DEF_KEY")
	private String targetTaskDefKey;
	/** 流转的目标节点 名称 **/
	@Column(name = "TARGET_TASK_NAME")
	private String targetTaskName;
	/** 审核节点的退回原因 **/
	@Column(name = "REVIEW_FALLBACK_REASON")
	private String reviewFallbackReasoin;
	/** 审核节点的代办人类型**/
	@Column(name= "REVIEW_TASK_ASSIGN_TYPE")
	private String reviewTaskAssignType;
	
	
	
	//增加代办人字段
	@Column(name = "CAND_USER_ID")
	private String candUserId; 
	
	//增加代办人字段
	@Column(name = "IS_TASK_FINISH")
	private String isTaskFinish;

	public String getCandUserId() {
		return candUserId;
	}

	public void setCandUserId(String candUserId) {
		this.candUserId = candUserId;
	}

	public String getReviewTaskAssignType() {
		return reviewTaskAssignType;
	}

	public void setReviewTaskAssignType(String reviewTaskAssignType) {
		this.reviewTaskAssignType = reviewTaskAssignType;
	}

	public String getReviewFallbackReasoin() {
		return reviewFallbackReasoin;
	}

	public void setReviewFallbackReasoin(String reviewFallbackReasoin) {
		this.reviewFallbackReasoin = reviewFallbackReasoin;
	}

	public String getTargetTaskId() {
		return targetTaskId;
	}

	public void setTargetTaskId(String targetTaskId) {
		this.targetTaskId = targetTaskId;
	}

	public String getTargetTaskDefKey() {
		return targetTaskDefKey;
	}

	public void setTargetTaskDefKey(String targetTaskDefKey) {
		this.targetTaskDefKey = targetTaskDefKey;
	}

	public String getTargetTaskName() {
		return targetTaskName;
	}

	public void setTargetTaskName(String targetTaskName) {
		this.targetTaskName = targetTaskName;
	}

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	/**
	 * 取得主键
	 * 
	 * @return 主键
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置主键
	 * 
	 * @param id
	 *            主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 取得流程定义Id
	 * 
	 * @return 流程定义Id
	 */
	public String getPdId() {
		return pdId;
	}

	/**
	 * 设置流程定义Id
	 * 
	 * @param pdId
	 *            流程定义Id
	 */
	public void setPdId(String pdId) {
		this.pdId = pdId;
	}

	/**
	 * 取得流程定义key
	 * 
	 * @return 流程定义key
	 */
	public String getPdKey() {
		return pdKey;
	}

	/**
	 * 设置流程定义key
	 * 
	 * @param pdKey
	 *            流程定义key
	 */
	public void setPdKey(String pdKey) {
		this.pdKey = pdKey;
	}

	/**
	 * 取得流程定义名称
	 * 
	 * @return 流程定义名称
	 */
	public String getPdName() {
		return pdName;
	}

	/**
	 * 设置流程定义名称
	 * 
	 * @param pdName
	 *            流程定义名称
	 */
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	/**
	 * 取得流程定义版本
	 * 
	 * @return 流程定义版本
	 */
	public int getPdVersion() {
		return pdVersion;
	}

	/**
	 * 设置流程定义版本
	 * 
	 * @param pdVersion
	 *            流程定义版本
	 */
	public void setPdVersion(int pdVersion) {
		this.pdVersion = pdVersion;
	}

	/**
	 * 取得流程实例ID
	 * 
	 * @return 流程实例ID
	 */
	public String getPiId() {
		return piId;
	}

	/**
	 * 设置流程实例ID
	 * 
	 * @param piId
	 *            流程实例ID
	 */
	public void setPiId(String piId) {
		this.piId = piId;
	}

	/**
	 * 取得执行实例ID
	 * 
	 * @return 执行实例ID
	 */
	public String getExecId() {
		return execId;
	}

	/**
	 * 设置执行实例ID
	 * 
	 * @param execId
	 *            执行实例ID
	 */
	public void setExecId(String execId) {
		this.execId = execId;
	}

	/**
	 * 取得业务关联Key
	 * 
	 * @return 业务关联Key
	 */
	public String getBizKey() {
		return bizKey;
	}

	/**
	 * 设置业务关联Key
	 * 
	 * @param bizKey
	 *            业务关联Key
	 */
	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}

	/**
	 * 取得审核节点ID
	 * 
	 * @return 审核节点ID
	 */
	public String getReviewTaskId() {
		return reviewTaskId;
	}

	/**
	 * 设置审核节点ID
	 * 
	 * @param reviewTaskId
	 *            审核节点ID
	 */
	public void setReviewTaskId(String reviewTaskId) {
		this.reviewTaskId = reviewTaskId;
	}

	/**
	 * 取得审核节点Key
	 * 
	 * @return
	 */
	public String getReviewTaskDefKey() {
		return reviewTaskDefKey;
	}

	/**
	 * 设置审核节点Key
	 * 
	 * @param reviewTaskDefKey
	 *            审核节点Key
	 */
	public void setReviewTaskDefKey(String reviewTaskDefKey) {
		this.reviewTaskDefKey = reviewTaskDefKey;
	}

	/**
	 * 取得审核节点名称
	 * 
	 * @return 审核节点名称
	 */
	public String getReviewTaskName() {
		return reviewTaskName;
	}

	/**
	 * 设置审核节点名称
	 * 
	 * @param reviewTaskName
	 *            审核节点名称
	 */
	public void setReviewTaskName(String reviewTaskName) {
		this.reviewTaskName = reviewTaskName;
	}

	/**
	 * 取得审核人ID
	 * 
	 * @return 审核人ID
	 */
	public String getReviewUserId() {
		return reviewUserId;
	}

	/**
	 * 设置审核人ID
	 * 
	 * @param reviewUserId
	 *            审核人ID
	 */
	public void setReviewUserId(String reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	/**
	 * 取得审核人名称
	 * 
	 * @return 审核人名称
	 */
	public String getReviewUserName() {
		return reviewUserName;
	}

	/**
	 * 设置审核人名称
	 * 
	 * @param reviewUserName
	 *            审核人名称
	 */
	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}

	/**
	 * 取得审核公司（单位）ID
	 * 
	 * @return 审核公司（单位）ID
	 */
	public String getReviewCompanyId() {
		return reviewCompanyId;
	}

	/**
	 * 设置审核公司（单位）ID
	 * 
	 * @param reviewCompanyId
	 *            审核公司（单位）ID
	 */
	public void setReviewCompanyId(String reviewCompanyId) {
		this.reviewCompanyId = reviewCompanyId;
	}

	/**
	 * 取得审核公司（单位）名称
	 * 
	 * @return 审核公司（单位）名称
	 */
	public String getReviewCompanyName() {
		return reviewCompanyName;
	}

	/**
	 * 设置审核公司（单位）名称
	 * 
	 * @param reviewCompanyName
	 *            审核公司（单位）名称
	 */
	public void setReviewCompanyName(String reviewCompanyName) {
		this.reviewCompanyName = reviewCompanyName;
	}

	/**
	 * 取得审核部门ID
	 * 
	 * @return 审核部门ID
	 */
	public String getReviewDepartmentId() {
		return reviewDepartmentId;
	}

	/**
	 * 设置审核部门ID
	 * 
	 * @param reviewDepartmentId
	 *            审核部门ID
	 */
	public void setReviewDepartmentId(String reviewDepartmentId) {
		this.reviewDepartmentId = reviewDepartmentId;
	}

	/**
	 * 取得审核部门名称
	 * 
	 * @return 审核部门名称
	 */
	public String getReviewDepartmentName() {
		return reviewDepartmentName;
	}

	/**
	 * 设置审核部门名称
	 * 
	 * @param reviewDeparmentName
	 */
	public void setReviewDepartmentName(String reviewDeparmentName) {
		this.reviewDepartmentName = reviewDeparmentName;
	}

	/**
	 * 取得审核时间
	 * 
	 * @return 审核时间
	 */
	public Date getReviewDate() {
		return reviewDate;
	}

	/**
	 * 设置审核时间
	 * 
	 * @param reviewDate
	 *            审核时间
	 */
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	/**
	 * 取得审核标题
	 * 
	 * @return 审核标题
	 */
	public String getReviewTitle() {
		return reviewTitle;
	}

	/**
	 * 设置审核标题
	 * 
	 * @param reviewTitle
	 *            审核标题
	 */
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	/**
	 * 取得审核内容
	 * 
	 * @return 审核内容
	 */
	public String getReviewContent() {
		return reviewContent;
	}

	/**
	 * 设置审核内容
	 * 
	 * @param reviewContent
	 *            审核内容
	 */
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	/**
	 * 取得审核注释
	 * 
	 * @return 审核注释
	 */
	public String getReviewComment() {
		return reviewComment;
	}

	/**
	 * 设置审核注释
	 * 
	 * @param reviewComment
	 *            审核注释
	 */
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	/**
	 * 取得审核结果.0：通过，1：驳回
	 * 
	 * @see cn.com.bpsc.ufp.bpm.common.Constant
	 * @return 审核结果.0：通过，1：驳回
	 */
	public String getReviewResult() {
		return reviewResult;
	}

	/**
	 * 设置审核结果.0：通过，1：驳回
	 * 
	 * @see cn.com.bpsc.ufp.bpm.common.Constant
	 * 
	 * @param reviewResult
	 *            审核结果.0：通过，1：驳回
	 */
	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}

	/**
	 * hashCode方法。 取Id的hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bizKey == null) ? 0 : bizKey.hashCode());
		return result;
	}

	/**
	 * 对象相等方法。 如果ID相同则对象相等。
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BpmTaskReview other = (BpmTaskReview) obj;
		if (bizKey == null) {
			if (other.bizKey != null)
				return false;
		} else if (!bizKey.equals(other.bizKey))
			return false;
		return true;
	}

	public String getIsTaskFinish() {
		return isTaskFinish;
	}

	public void setIsTaskFinish(String isTaskFinish) {
		this.isTaskFinish = isTaskFinish;
	}

}
