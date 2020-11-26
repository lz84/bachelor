package org.bachelor.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.common.BpmConstant;
import org.bachelor.bpm.common.BpmUtils;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.domain.TaskType;
import org.bachelor.bpm.service.IExpressionResolver;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.bpm.service.IGroupExpResolveService;
import org.bachelor.context.service.IVLService;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BpmRuntimeTaskServiceImpl implements IBpmRuntimeTaskService {

	private static Log log = LogFactory.getLog(BpmRuntimeTaskServiceImpl.class);
	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private FormService formService;

	@Autowired
	private IVLService vlService;

	@Autowired
	private IExpressionResolver authService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private IGroupExpResolveService groupExpResolveService;

	@Autowired
	private IBpmRuntimeService bpmRuntimeService;

	@Override
	public List<TaskEx> getTaskByCandidateUser(String userId) {
		List<Task> taskList = taskService.createTaskQuery()
				.taskCandidateUser(userId).orderByTaskCreateTime().desc()
				.orderByTaskName().asc().list();

		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		for (Task task : taskList) {
			TaskEx taskEx = new TaskEx();
			taskExList.add(taskEx);
			String formKey = this.getTaskURL(task);
			taskEx.setFormKey(formKey);
			taskEx.setTask(task);
		}

		return taskExList;
	}

	@Override
	public List<TaskEx> getTaskByCandidateRole(String roleId) {
		List<Task> taskList = taskService.createTaskQuery()
				.taskCandidateGroup(roleId).orderByTaskCreateTime().desc()
				.orderByTaskName().asc().list();

		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		for (Task task : taskList) {
			TaskEx taskEx = new TaskEx();
			taskExList.add(taskEx);
			String formKey = this.getTaskURL(task);
			taskEx.setFormKey(formKey);
			taskEx.setTask(task);
		}

		return taskExList;
	}

	@Override
	public List<TaskEx> getTaskByAssignee(String assigneeId) {
		List<Task> taskList = taskService.createTaskQuery()
				.taskAssignee(assigneeId).orderByTaskCreateTime().desc()
				.orderByTaskName().asc().list();

		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		for (Task task : taskList) {
			TaskEx taskEx = new TaskEx();
			taskExList.add(taskEx);
			String formKey = this.getTaskURL(task);
			taskEx.setFormKey(formKey);
			taskEx.setTask(task);
		}

		return taskExList;
	}

	@Override
	public List<TaskEx> getTaskByCandidateUser(String pdkey, String userId) {

		List<Task> taskList = taskService.createTaskQuery()
				.processDefinitionKey(pdkey).taskCandidateUser(userId)
				.orderByTaskCreateTime().desc().orderByTaskName().asc().list();

		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		for (Task task : taskList) {
			TaskEx taskEx = new TaskEx();
			taskExList.add(taskEx);
			String formKey = this.getTaskURL(task);
			taskEx.setFormKey(formKey);
			taskEx.setTask(task);
		}

		return taskExList;
	}

	@Override
	public List<TaskEx> getTaskByCandidateRole(String pdkey, String roleId) {
		List<Task> taskList = taskService.createTaskQuery()
				.processDefinitionKey(pdkey).taskCandidateGroup(roleId)
				.orderByTaskCreateTime().desc().orderByTaskName().asc().list();

		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		for (Task task : taskList) {
			TaskEx taskEx = new TaskEx();
			taskExList.add(taskEx);
			String formKey = this.getTaskURL(task);
			taskEx.setFormKey(formKey);
			taskEx.setTask(task);
		}

		return taskExList;
	}

	@Override
	public List<TaskEx> getTaskByAssignee(String pdkey, String assigneeId) {
		List<Task> taskList = taskService.createTaskQuery()
				.processDefinitionKey(pdkey).taskAssignee(assigneeId)
				.orderByTaskCreateTime().desc().orderByTaskName().asc().list();
		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		for (Task task : taskList) {
			TaskEx taskEx = new TaskEx();
			taskExList.add(taskEx);
			String formKey = this.getTaskURL(task);
			taskEx.setFormKey(formKey);
			taskEx.setTask(task);
		}
		return taskExList;
	}

	@Override
	public TaskEx getTask(String taskId, String userId, String assignmentType) {
		Task task = this.getTaskQuery(assignmentType, userId).taskId(taskId)
				.orderByTaskCreateTime().desc().orderByTaskName().asc()
				.singleResult();
		if (task == null && StringUtils.isBlank(assignmentType)) {
			task = taskService.createTaskQuery().taskCandidateUser(userId)
					.taskId(taskId).orderByTaskCreateTime().orderByTaskName()
					.asc().singleResult();
		}
		if (task == null) {
			return null;
		}
		TaskEx taskEx = new TaskEx();
		String formKey = this.getTaskURL(task);
		taskEx.setFormKey(formKey);
		taskEx.setTask(task);
		return taskEx;
	}

	@Override
	public TaskEx getTask(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (task == null) {
			return null;
		}
		TaskEx taskEx = new TaskEx();
		String formKey = this.getTaskURL(task);
		taskEx.setFormKey(formKey);
		taskEx.setTask(task);
		return taskEx;
	}

	@Override
	public Comment addComment(String taskId, String processInstanceId,
			String message) {
		return taskService.addComment(taskId, processInstanceId, message);
	}

	/**
	 * 根据查询条件和类型返回对应的TaskQuery实现
	 * 
	 * @param assignmentType
	 * @return TaskQuery实现
	 */
	private TaskQuery getTaskQuery(String assignmentType, String queryArg) {
		TaskQuery query = taskService.createTaskQuery();
		if (StringUtils.isBlank(assignmentType)) {
			query.taskAssignee(queryArg);
		} else {
			if (BpmConstant.BPM_ASSIGNMENT_TYPE_ASSIGNED.equals(assignmentType)) {
				query.taskAssignee(queryArg);
			} else if (BpmConstant.BPM_ASSIGNMENT_TYPE_CANDIDATEUSER
					.equals(assignmentType)) {
				query.taskCandidateUser(queryArg);
			}
		}
		return query;
	}

	/**
	 * 接口的私有方法 作用：获取会签节点定义，的
	 * 
	 * @param taskEx
	 * @return
	 */
	private List<IBaseEntity> getUserFormJointTask(TaskEx taskEx) {
		List<IBaseEntity> userList = new ArrayList<IBaseEntity>();
		IBaseEntity assigneer = authService.resolveUserByUserExp(taskEx.getTask().getAssignee());
		if (assigneer != null) {
			userList.add(assigneer);
			return userList;
		}
		List<IdentityLink> ientityLinkList = taskService
				.getIdentityLinksForTask(taskEx.getTask().getId());
		for (IdentityLink identityLink : ientityLinkList) {
			IBaseEntity candidateUser = authService.resolveUserByUserExp(identityLink.getUserId());
			if (candidateUser != null) {
				userList.add(candidateUser);
			}
		}
		return userList;
	}

//	@Override
//	public List<? extends IBaseEntity> getUsersByTaskId(String taskId) {
//		BaseBpDataEx bpDataEx = bpmRuntimeService.getBpDataExByTaskId(taskId, null);
//		List<IBaseEntity> userList = new ArrayList<IBaseEntity>();
//		// 判断是会审节点
//		if (bpDataEx.getTaskEx().getTaskType() != null
//				&& TaskType.会审.equals(bpDataEx.getTaskEx().getTaskType())) {
//			try {
//				List<TaskEx> taskExList = getAllActiveTask(bpDataEx.getTaskEx()
//						.getTask().getProcessInstanceId());
//				for (TaskEx taskEx : taskExList) {
//					userList=this.getUserFormJointTask(taskEx);
//				}
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			}
//
//			return userList;
//		}
//
//		List<IdentityLink> ientityLinkList = taskService
//				.getIdentityLinksForTask(taskId);
//		Set<IBaseEntity> roleSet = new HashSet<IBaseEntity>();
//		Set<IBaseEntity> userSet = new HashSet<IBaseEntity>();
//		List<String> roleList = new ArrayList<String>();
//		for (IdentityLink iLink : ientityLinkList) {
//			if (iLink.getGroupId() != null) {
//				if (BpmUtils.isCandidateGroup(iLink)) {
//					if (BpmUtils.haveOrgInfo(iLink.getGroupId())) {
//						String groupId = groupExpResolveService
//								.resolveGroupExp(iLink.getGroupId(), bpDataEx);
//						if (!StringUtils.isEmpty(groupId)) {
//							roleList.add(groupId);
//						}
//					} else {
//						roleList.add(iLink.getGroupId());
//					}
//
//				}
//			}
//			if (iLink.getUserId() != null) {
//				userSet.add(authService.resolveUserByUserExp(iLink.getUserId()));
//			}
//		}
//
//		if (roleList != null) {
//			roleSet.addAll(authService
//					.resolveUsersByGroupExp(roleList
//							.toArray(new String[0])));
//		}
//
//		roleSet.addAll(userSet);
//		Map<String, IBaseEntity> userMap = new HashMap<String, IBaseEntity>();
//		for (IBaseEntity user : roleSet) {
//			userMap.put(user.getId(), user);
//		}
//		for (String string : userMap.keySet()) {
//			userList.add(userMap.get(string));
//		}
//		return userList;
//	}

	@Override
	public TaskDefinition getTaskDefition(String pdId, String taskDefKey) {
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(pdId);
		List<ActivityImpl> activitiList = def.getActivities();
		TaskDefinition taskDefinition = null;
		if (activitiList != null && !activitiList.isEmpty()) {
			for (ActivityImpl activityImpl : activitiList) {
				if (activityImpl.getId().equals(taskDefKey)) {
					taskDefinition = (TaskDefinition) activityImpl
							.getProperty("taskDefinition");
					break;
				}
			}
		}
		return taskDefinition;
	}

	@Override
	public PvmActivity getFirstTask(String pdId) {
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(pdId);
		PvmActivity activity = def.getInitial().getOutgoingTransitions().get(0)
				.getDestination();

		return activity;
	}

	@Override
	public String getTaskURL(Task task) {
		String url = formService.getTaskFormKey(task.getProcessDefinitionId(),
				task.getTaskDefinitionKey());
		return url;
	}

//	@Override
//	public List<? extends IBaseEntity> getUserByTaskDefinition(TaskDefinition taskDefinition,
//			String piId) {
//		List<IBaseEntity> userList = new ArrayList<IBaseEntity>();
//		List<String> roleList = new ArrayList<String>();
//		// 获取权限和组织机构表达形式
//		Set<Expression> gruopExpressionSet = taskDefinition
//				.getCandidateGroupIdExpressions();
//		for (Expression expression : gruopExpressionSet) {
//			String gruop = expression.getExpressionText();
//			// 判断表达式是否符合约定的公司或组织机构
//			if (BpmUtils.haveOrgInfo(gruop)) {
//				String groupId = groupExpResolveService.resolveGroupExp(gruop,
//						piId);
//				if (!StringUtils.isEmpty(groupId)) {
//					roleList.add(groupId);
//				}
//			} else {
//				roleList.add(gruop);
//			}
//		}
//		Set<Expression> userExpressionSet = taskDefinition
//				.getCandidateUserIdExpressions();
//		for (Expression expression : userExpressionSet) {
//			String user = expression.getExpressionText();
//			if (user != null) {
//				userList.add(authService.resolveUserByUserExp(user));
//			}
//		}
//		// 根据groupExpression（公司和角色）的配置取得代办人
//		userList.addAll(authService
//				.resolveUsersByGroupExp(roleList
//						.toArray(new String[roleList.size()])));
//
//		Map<String, IBaseEntity> userMap = new HashMap<String, IBaseEntity>();
//		for (IBaseEntity user : userList) {
//			userMap.put(user.getId(), user);
//		}
//
//		return new ArrayList<IBaseEntity>(userMap.values());
//	}

//	@Override
//	public List<IBaseEntity> getUserByTaskDefinition2(TaskDefinition taskDefinition,
//			String piId) {
//		Set<IBaseEntity> roleSet = new HashSet<IBaseEntity>();
//		Set<IBaseEntity> userSet = new HashSet<IBaseEntity>();
//		List<String> roleList = new ArrayList<String>();
//		List<IBaseEntity> userList = new ArrayList<IBaseEntity>();
//
//		Set<Expression> gruopExpressionSet = taskDefinition
//				.getCandidateGroupIdExpressions();
//		for (Expression expression : gruopExpressionSet) {
//			String gruop = expression.getExpressionText();
//			if (BpmUtils.haveOrgInfo(gruop)) {
//				String groupId = groupExpResolveService.resolveGroupExp(gruop,
//						piId);
//				if (!StringUtils.isEmpty(groupId)) {
//					roleList.add(groupId);
//				}
//			} else {
//				roleList.add(gruop);
//			}
//		}
//		Set<Expression> userExpressionSet = taskDefinition
//				.getCandidateUserIdExpressions();
//		for (Expression expression : userExpressionSet) {
//			String user = expression.getExpressionText();
//			if (user != null) {
//				userSet.add(authService.resolveUserByUserExp(user));
//			}
//		}
//		// 增加对activit参数的解析
//		for (String roleStr : roleList) {
//			if (!StringUtils.contains(roleStr, "#")
//					&& StringUtils.contains(roleStr, "$")) {
//				String variableExp = StringUtils.substringBeforeLast(
//						StringUtils.substringAfterLast(roleStr, "{"), "}");
//				roleSet.addAll(authService
//						.resolveUsersByGroupExp(bpmRuntimeService
//								.getPiVariable(piId, variableExp).toString()));
//			}
//		}
//		roleSet.addAll(authService.resolveUsersByGroupExp(roleList
//				.toArray(new String[roleList.size()])));
//		roleSet.addAll(userSet);
//		Map<String, IBaseEntity> userMap = new HashMap<String, IBaseEntity>();
//		for (IBaseEntity user : roleSet) {
//			userMap.put(user.getId(), user);
//		}
//		for (String string : userMap.keySet()) {
//			userList.add(userMap.get(string));
//		}
//		return userList;
//	}

	@Override
	public void setTaskVariable(String executionId, String variableName,
			String variableValue) {
		runtimeService.setVariable(executionId, variableName, variableValue);
	}

	@Override
	public List<TaskEx> getActiveTask(String piId, String userId) {
		TaskQuery query = taskService.createTaskQuery();
		List<Task> taskList = query.processInstanceId(piId).active().list();
		List<TaskEx> result = new ArrayList<TaskEx>();
		if (taskList == null || taskList.isEmpty()) {
			return result;
		}
		for (Task t : taskList) {
			String assingee = t.getAssignee();
			if (userId == null || 
					(assingee!= null && t.getAssignee().equals(userId))) {
				result.add(warpTask(t));
			}
		}
		return result;
	}
	
	@Override
	public List<TaskEx> getActiveTask(String piId) {
		IBaseEntity user = (IBaseEntity) vlService
				.getSessionAttribute(BpmConstant.BPM_LOGON_USER);
		String userId = null;
		if(user != null){
			userId = user.getId();
		}
		return getActiveTask(piId, userId);
	}

	public List<TaskEx> getAllActiveTask(String taskDefKey) {
		List<TaskEx> taskExList = new ArrayList<TaskEx>();
		TaskQuery query = taskService.createTaskQuery();
		List<Task> taskList = query.taskDefinitionKey(taskDefKey).active().list();
		if (taskList != null && !taskList.isEmpty()) {
			for (Task task : taskList) {
				taskExList.add(warpTask(task));
			}

		}
		return taskExList;
	}

	private TaskEx warpTask(Task task) {
		TaskEx taskEx = new TaskEx();
		String formKey = this.getTaskURL(task);
		taskEx.setFormKey(formKey);
		taskEx.setTask(task);
		taskEx.setTaskType(getTaskType(task.getId()));
		return taskEx;
	}

//	@Override
//	public List<String> getRoleByTaskDefinition(TaskDefinition taskDefinition,
//			String piId) {
//		BaseBpDataEx bpDataEx = bpmRuntimeService.getBpDataEx(piId, null);
//		List<String> roleNameList = new ArrayList<String>();
//
//		Set<Expression> gruopExpressionSet = taskDefinition
//				.getCandidateGroupIdExpressions();
//		for (Expression expression : gruopExpressionSet) {
//			String gruop = expression.getExpressionText();
//			if (BpmUtils.haveOrgInfo(gruop)) {
//				String groupId = groupExpResolveService.resolveGroupExp(gruop,
//						bpDataEx);
//				if (!StringUtils.isEmpty(groupId)) {
//					roleNameList.add(groupId);
//				}
//			} else {
//				roleNameList.add(gruop);
//			}
//		}
//
//		List<String> roleDescList = new ArrayList<String>();
//		for (String roleNameStr : roleNameList) {
//			String roleName = toRoleDesc(roleNameStr);
//			roleDescList.add(roleName);
//		}
//		return roleNameList;
//	}

	private String toRoleDesc(String roleIdStr) {
		String roleDesc = "";
		//要重新实现
//		if (BpmUtils.haveOrgInfo(roleIdStr)) {
//			String roleId = StringUtils.substringBeforeLast(roleIdStr, "#");
//			String orgId = StringUtils.substringAfterLast(roleIdStr, "#");
//
//			String desc = authService.findRoleById(roleId).getDescription();
//			String orgName = authService.findOrgById(orgId).getName();
//			roleDesc = desc + "#" + orgName;
//		} else {
//			roleDesc = authService.findRoleById(roleIdStr).getDescription();
//		}
		return roleDesc;
	}

	@Override
	public TaskType getTaskType(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		if (formData == null) {
			return null;
		}
		List<FormProperty> formProps = formData.getFormProperties();
		for (FormProperty fp : formProps) {

			if (fp.getId().equals("taskType")) {
				return TaskType.valueOf(fp.getName());
			}
		}
		return null;
	}

//	@Override
//	public List<BaseBpDataEx> getBpDataExByCandidateUser(String pdkey,
//			String candidateUserId) {
//		// 获取用户权限信息
//		List<? extends IBaseEntity> roles = authService
//				.findRolesByUserId(candidateUserId);
//		/** 查询所有节点数据 **/
//		TaskQuery taskUserQuery = taskService.createTaskQuery();
//		TaskQuery taskGroupQuery = taskService.createTaskQuery();
//		List<String> roleIds = new ArrayList<String>();
//
//		if (!StringUtils.isEmpty(pdkey)) {
//			taskUserQuery.processDefinitionKey(pdkey);
//			taskGroupQuery.processDefinitionKey(pdkey);
//		}
//		if (!StringUtils.isEmpty(candidateUserId)) {
//			taskUserQuery.taskCandidateUser(candidateUserId);
//		}
//		List<Task> tasks = null;
//		// 如果权限不为空，将权限名作为查询，进行查询
//		if (roles != null && roles.size() > 0) {
//			for (IBaseEntity role : roles) {
//				roleIds.add(role.getName());
//			}
//			tasks = taskGroupQuery.taskCandidateGroupIn(roleIds)
//					.orderByTaskCreateTime().asc().orderByTaskName().asc()
//					.list();
//		}
//		;
//		List<Task> taskList = taskUserQuery.orderByTaskCreateTime().asc()
//				.orderByTaskName().asc().list();
//		if (tasks != null && tasks.size() > 0) {
//			for (Task task : taskList) {
//				for (Task tempTask : tasks) {
//					if (task.getId().equals(tempTask.getId())) {
//						tasks.remove(tempTask);
//						break;
//					}
//				}
//			}
//			taskList.addAll(tasks);
//		}
//
//		List<BaseBpDataEx> bpDataExs = new ArrayList<BaseBpDataEx>();
//		BaseBpDataEx bpDataEx = null;
//
//		for (Task task : taskList) {
//			bpDataEx = (BaseBpDataEx) bpmRuntimeService.getBpDataEx(task
//					.getProcessInstanceId(), null);
//			bpDataEx.setPiId(task.getProcessInstanceId());
//			TaskEx taskEx = warpTask(task);
//			bpDataEx.setTaskEx(taskEx);
//			bpDataExs.add(bpDataEx);
//		}
//		return bpDataExs;
//	}

	@Override
	public List<BaseBpDataEx> getBpDataExByAssignee(String pdkey,
			String assignee) {
		List<Task> taskList = taskService.createTaskQuery()
				.processDefinitionKey(pdkey).taskAssignee(assignee)
				.orderByTaskCreateTime().asc().orderByTaskName().asc().list();

		List<BaseBpDataEx> bpDataExs = new ArrayList<BaseBpDataEx>();
		BaseBpDataEx bpDataEx = null;

		for (Task task : taskList) {
			bpDataEx = (BaseBpDataEx) bpmRuntimeService.getBpDataEx(task
					.getProcessInstanceId(), null);
			bpDataEx.setPiId(task.getProcessInstanceId());
			TaskEx taskEx = warpTask(task);
			bpDataEx.setTaskEx(taskEx);
			bpDataExs.add(bpDataEx);
		}
		return bpDataExs;
	}

	@Override
	public List<TaskDefinition> getFirstTaskDef(String bizKey) {
		if (bizKey == null || bizKey == "") {
			return null;
		}
		ProcessInstance piInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(piInstance
						.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities();
		List<TaskDefinition> taskDefinition = new ArrayList<TaskDefinition>();
		if (activitiList != null && !activitiList.isEmpty()) {
			for (ActivityImpl activityImpl : activitiList) {
				if ("userTask".equals(activityImpl.getProperty("type"))) {
					List<PvmTransition> inComingList = activityImpl
							.getIncomingTransitions();
					if (inComingList != null && inComingList.size() > 0) {
						for (PvmTransition pvm : inComingList) {
							if ("startEvent".equals(pvm.getSource()
									.getProperty("type"))) {
								taskDefinition
										.add((TaskDefinition) activityImpl
												.getProperty("taskDefinition"));
							}
						}
					}
				}
			}
		}
		if (taskDefinition.size() > 0)
			return taskDefinition;
		return null;
	}

	@Override
	public List<TaskDefinition> getLastTaskDef(String bizKey) {
		// TODO 实现该方法
		if (bizKey == null || bizKey == "") {
			return null;
		}
		ProcessInstance piInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceBusinessKey(bizKey).singleResult();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((org.activiti.engine.impl.RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(piInstance
						.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities();
		List<TaskDefinition> taskDefinition = new ArrayList<TaskDefinition>();
		if (activitiList != null && !activitiList.isEmpty()) {
			for (ActivityImpl activityImpl : activitiList) {
				if ("userTask".equals(activityImpl.getProperty("type"))) {
					List<PvmTransition> outGoingList = activityImpl
							.getOutgoingTransitions();
					if (outGoingList != null && outGoingList.size() > 0) {
						for (PvmTransition pvm : outGoingList) {
							if ("endEvent".equals(pvm.getDestination()
									.getProperty("type"))) {
								taskDefinition
										.add((TaskDefinition) activityImpl
												.getProperty("taskDefinition"));
							}
						}
					}
				}
			}
		}
		if (taskDefinition.size() > 0)
			return taskDefinition;
		return null;

	}

}
