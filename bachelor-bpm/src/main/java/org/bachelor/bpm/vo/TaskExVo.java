package org.bachelor.bpm.vo;

import java.io.Serializable;

import org.bachelor.bpm.domain.TaskEx;

public class TaskExVo extends TaskEx implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5236395238880365485L;

	/** 边框颜色类型 0已执行节点 1未执行节点 **/
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
