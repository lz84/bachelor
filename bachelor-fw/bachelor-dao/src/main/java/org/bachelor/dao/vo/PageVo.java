/*
 * @(#)PageVo.java	May 31, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.dao.vo;

/**
 * 分页信息类
 * 
 * @author Team Bachelor
 *
 */
public class PageVo {

	/**记录总数 **/
	private long count = 0;
	/**每页显示记录数 **/
	private int pageRowNum = 10;
	/**总页数 **/
	private long pageCount = 0;
	/**分页开始行数 **/
	private int startIndex = 0;
	/**分页结束行数 **/
	private int endIndex = 0;
	/**当前页 **/
	private int page = -1;
	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}
	/**
	 * @return the pageRowNum
	 */
	public int getPageRowNum() {
		return pageRowNum;
	}
	/**
	 * @param pageRowNum the pageRowNum to set
	 */
	public void setPageRowNum(int pageNum) {
		this.pageRowNum = pageNum;
	}
	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}
	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	/**
	 * @return the pageCount
	 */
	public long getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	
}
