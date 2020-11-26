package org.bachelor.bpm.service;

import java.util.List;

import org.bachelor.core.entity.IBaseEntity;


/**
 * 用于在流程中获取用户、权限等授权信息，需要由用户实现。
 * @author liuzhuo
 *
 */
public interface IExpressionResolver {
	
	/**
	 * 通过用户表达式找到用户
	 * @param userId
	 * @return
	 */
	public IBaseEntity resolveUserByUserExp(String userId);
	
	/**
	 * 通过组表达式找到指定用户
	 * @param groupExp
	 * @return
	 */
	public List<? extends IBaseEntity> resolveUsersByGroupExp(
			String... groupExp);
}
