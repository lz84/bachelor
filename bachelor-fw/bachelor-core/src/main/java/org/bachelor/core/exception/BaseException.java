/*
 * @(#)BaseException.java	Apr 18, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.core.exception;

/**
 * 开发平台基础异常类
 * 
 * @author Team Bachelor
 *
 */
public class BaseException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1345519088905785859L;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
}
