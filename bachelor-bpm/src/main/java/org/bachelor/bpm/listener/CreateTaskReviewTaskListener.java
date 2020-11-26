package org.bachelor.bpm.listener;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.service.IBpmEngineService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.bpm.service.IGroupExpResolveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTaskReviewTaskListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IGroupExpResolveService groupExpResolveService;
	@Autowired
	private IBpmRuntimeService bpmRuntimeService;
	
	@Autowired
	private IBpmEngineService bpmEngineService;
	
	private FormService formService;
	@Autowired
	private IBpmRuntimeTaskService bpmRuntimeTaskService;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void notify(DelegateTask delegateTask) {
//		
//		BaseBpDataEx bpDataEx = bpmRuntimeService.getBpDataEx(delegateTask.getProcessInstanceId(), null);
//		TaskDefinition td = bpmRuntimeTaskService.getTaskDefition(
//				delegateTask.getProcessDefinitionId(), 
//				delegateTask.getTaskDefinitionKey());
////		TaskFormData fd = formService.getTaskFormData(delegateTask.getId());
////		List<FormProperty> fps = fd.getFormProperties();
////		for(FormProperty fp : fps){
////			
////		}
//		td.get
//		// 将流程数据转换为map
//		Map<String, Object> bpDataExMap = null;
//		try {
//			// 获取节点出线的终点
//			bpDataExMap = PropertyUtils.describe(bpDataEx);
//		} catch (Exception e) {
//			bpDataExMap = new HashMap<String, Object>();
//			log.error(e.getMessage(), e);
//		}
//		/*********** 判断流转的终点节点是否是会签节点 ***************/
//		// 是否为会签节点
//		Boolean isCounterSignTask = false;
//		String outGoingTransValue = (String) bpDataExMap.get("NEXT_STEP");
//		ActivityImpl actImpl = bpmEngineService.getOutGoingActivityImpl(
//				bpDataEx.getDomainId(), outGoingTransValue);
//		if (actImpl != null) {
//			try {
//				isCounterSignTask = bpmEngineService
//						.isJointTask((TaskDefinition) actImpl
//								.getProperty("taskDefinition"));
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			}
//		}
//
//		// 解析强制代办人，传递了taskCandidate参数后，其他与代办人相关的设置均无效
//		Set<String> forceCandidate = new HashSet<String>();
//		if (!StringUtils.isEmpty(taskCandidate)) {
//			// taskCandidate.replaceAll(" ", "");
//			// taskCandidate.replaceAll(";;", ";");
//			String[] assigneerStr = taskCandidate.split(";");
//			forceCandidate = new HashSet(Arrays.asList(assigneerStr));
//		}
//		// 如果是退回，则设置目标节点原来的办理人为待办人
//		if (result.equals(ReviewResult.reject)) {
//			//现决定去向
//			bpmTaskReviewDao.getTaskReviewsByTaskId(taskId);
//		}
//		// 为了生成会签的taskReview，将使用 assigneerList
//		List<String> assigneerList = new ArrayList<String>();
//		// 判断节点出线终点的节点是否是会签节点
//		if (isCounterSignTask) {
//			String MIAssigneeListName = bpmEngineService
//					.getMIAssigneeListName(actImpl);
//			// 此处修改扩展属性的存储方式
//			List<String> paramAssigneerList = (List) bpDataEx
//					.getBusinessExtMap().get(MIAssigneeListName);
//			if (StringUtils.isEmpty(taskCandidate)
//					&& paramAssigneerList != null
//					&& paramAssigneerList.size() > 0) {
//				assigneerList.addAll(paramAssigneerList);
//			} else {
//				assigneerList.addAll(forceCandidate);
//			}
//			Map<String, Object> businessExtMap = bpDataEx.getBusinessExtMap();
//			// 此处修改扩展属性的存储方式
//			businessExtMap.put(MIAssigneeListName, assigneerList);
//			// 获取流程的通用变量
//			Map<String, Object> GvMap = new HashMap<String, Object>();
//			// 将业务扩展变量转存入流程通用变量
//			addBunissMapToGv(businessExtMap, GvMap);
//			// 将流程数据封装对象，转存入流程通用变量
//			addToGvMap(bpDataEx, GvMap);
//			runtimeService.setVariables(bpDataEx.getPiId(), GvMap);
//			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY,
//					bpDataEx);
//		}
//		/** 如果nextTask是单签节点，assigneerList.size将为0 **/
//		List<TaskEx> task = bpmTaskService.getActiveTask(bpDataEx.getPiId(),
//				null);
//		if (task != null ) {
//			String taskId = task.getTask().getId();
//			TaskType type = bpmTaskService.getTaskType(taskId);
//			StringBuilder candUserId = new StringBuilder();
//
//			// 如果是前进
//			// 则需要设置强制待办人。
//			if (type == null && forceCandidate != null
//					&& forceCandidate.size() > 0) {
//				assigneerList = new ArrayList<String>(forceCandidate);
//				// 记录代办人需要用到candidateUserLink
//				List<IdentityLink> candidateUserLink = taskService
//						.getIdentityLinksForTask(taskId);
//				for (IdentityLink idLink : candidateUserLink) {
//					if (idLink.getUserId() == null
//							&& idLink.getGroupId() != null) {
//						taskService.deleteCandidateUser(taskId,
//								idLink.getGroupId());
//						continue;
//					}
//					if (!forceCandidate.contains(idLink.getUserId())) {
//						taskService.deleteCandidateUser(taskId,
//								idLink.getUserId());
//					} else {
//						forceCandidate.remove(idLink.getUserId());
//						continue;
//					}
//				}
//				for (String fcUserId : forceCandidate) {
//					taskService.addCandidateUser(taskId, fcUserId);
//				}
//			} else {
//				// 获取流程本身的代办人信息
//				// 记录代办人需要用到candidateUserLink
//				List<IdentityLink> candidateUserLink = taskService
//						.getIdentityLinksForTask(taskId);
//				for (IdentityLink idLink : candidateUserLink) {
//					if (idLink.getGroupId() != null) {
//						List<? extends IBaseEntity> users = groupExpResolveService
//								.resolve(idLink.getGroupId(), bpDataEx);
//						for (IBaseEntity user : users) {
//							assigneerList.add(user.getId());
//						}
//					}
//					if (idLink.getUserId() != null) {
//						assigneerList.add(idLink.getGroupId());
//					}
//				}
//				candUserId.append(StringUtils.join(assigneerList, '|')).append(
//						'|');
//
//			}
//
//			// 如果target节点是单签节点，则为target节点生成代办查询用的taskReview数据
//			// 如果forceCandidate没有数据，就将节点原本的candidateUserLink赋给
//			saveTaskReview(bpDataEx, candUserId.toString(), "0");
//		} else {
//			// 如果target节点是会签节点，那就在此生成对应会签节点所有代办人的taskReview
//			saveTaskReviewForCounterTask(bpDataEx, assigneerList, "0");
//		}
//		//得到当前任务的候选人或组织
//		Set<IdentityLink> ilList = delegateTask.getCandidates();
//		for (IdentityLink il : ilList) {
//			List<? extends IBaseEntity> users = groupExpResolveService.resolve(
//					il.getGroupId(), bpDataEx);
//			if (users != null && !users.isEmpty()) {
//				for (IBaseEntity user : users) {
//					taskService.addCandidateUser(delegateTask.getId(),
//							user.getId());
//				}
//			}
//		}

	}

}
