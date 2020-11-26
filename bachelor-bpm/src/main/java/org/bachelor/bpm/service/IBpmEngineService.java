package org.bachelor.bpm.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;

import org.bachelor.bpm.domain.BaseBpDataEx;

/**
 * 流程引擎底层核心实现封装 开发时间：2013-11-29 提供封装过的activiti底层实现，是其他服务接口的实现依赖
 * 
 * @author zhangtao
 */
public interface IBpmEngineService {
	public Map<PvmTransition, ActivityImpl> nextActivityImpl(
			ActivityImpl currentTaskDef);
	/**
	 * 将底层定义组件转换成节点定义 开发时间：2013-11-29
	 * 
	 * @param actImplMap
	 *            底层定义组件Map
	 * @param taskDefMap
	 *            节点定义Map
	 */
	public void warpTaskDefMap(Map<PvmTransition, ActivityImpl> actImplMap,
			Map<PvmTransition, TaskDefinition> taskDefMap);

	/**
	 * 根据流程绑定的业务key和出线表达式的值，判断并返回当前节点的流转终点 开发时间：2013-11-29
	 * 
	 * @param bizKey
	 *            流程绑定的流程业务key
	 * @param outGoingTransValue
	 *            判断节点出线的表达式的值
	 * @return 节点的底层定义组件
	 */
	public ActivityImpl getOutGoingActivityImpl(String bizKey,
			String outGoingTransValue);

	/**
	 * 根据流程绑定的业务key 查询所有流程当前节点的出线和出线终点，并返回封装后的集合 开发时间：2013-11-29
	 * 
	 * @param bizKey
	 *            流程绑定的流程业务key
	 * @return 节点的出线和出线终点的集合，出线作为key，终点定义对象作为value
	 */
	public Map<PvmTransition, ActivityImpl> getNextActivityImpl(String bizKey);

	/**
	 * 获取当前会签节点对应的执行人集合在流程变量中的名称 (此方法只能由会签节点使用) 开发时间：2013/11/20
	 * 
	 * @param actImpl
	 *            节点的底层定义组件
	 * @return 承载会签节点执行人的集合名称
	 */
	public String getMIAssigneeListName(ActivityImpl actImpl);

	/**
	 * 根据节点定义判断节点是否是会签节点 开发时间：2013/11/20
	 * 
	 * @param taskDef
	 *            节点定义
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Boolean isJointTask(TaskDefinition taskDef)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	/**
	 * 根据节点定义判断该节点是否设置了自动设置代办人 开发时间：2013/11/20
	 * 
	 * @param taskDef
	 *            节点定义
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Boolean isAutoSetAssignments(TaskDefinition taskDef)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	/**
	 * 根据当前节点的底层定义对象，清空该节点所有的出线对象，并返回原本的出线对象集合 开发时间：2013-11-29
	 * 
	 * @param currentTaskDef
	 *            当前节点的底层定义
	 * @return 节点的出线和出线终点的集合，出线作为key，终点定义对象作为value
	 */
	public List<PvmTransition> clearOutTransition(ActivityImpl activityImpl);

	/**
	 * 根据当前节点的底层定义对象，清空该节点所有的出线对象，并恢复节点原本的出线对象集合 开发时间：2013-11-29
	 * 
	 * @param currentTaskDef
	 *            当前节点的底层定义
	 * @return 节点的出线和出线终点的集合，出线作为key，终点定义对象作为value
	 */
	public void restoreOutTransition(ActivityImpl activityImpl,
			List<PvmTransition> transitions);

	/**
	 * 根据节点定义判断该节点是否设置了自动设置代办人 开发时间：2013/12/03
	 * 
	 * @param taskDef
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Boolean isReuseOperation(TaskDefinition taskDef)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	/**
	 * 根据节点定义判断该节点是否设置了自动设置代办人 开发时间：2013/12/03
	 * 
	 * @param taskDef
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Boolean isJointByGruop(TaskDefinition taskDef)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	/**
	 * 判断流程元素是否是外调子流程
	 * @param activityImpl
	 * @return
	 */
	public Boolean isCallActivityElement(ActivityImpl activityImpl);
	
	/**
	 * 根据节点id判断节点是否是会签节点
	 * @param taskId 
	 * @return
	 */
	public Boolean isCountersign(String taskId);
}
