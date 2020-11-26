package org.bachelor.bpm.domain;

/**
 * 审核结果
 * @author 
 *
 */
public enum ReviewResult {

	pass("通过"),reject("退回");
	
	private String name;
	private ReviewResult(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
