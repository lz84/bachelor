package org.bachelor.bpm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.common.BpmConstant;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.TaskEx;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IBpmRuntimeTaskService;
import org.bachelor.context.interceptor.AllManagedIntercepter;
import org.bachelor.context.service.IVLService;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

public class BpmParamInterceptor extends AllManagedIntercepter{

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IVLService vlService;
	
	@Autowired
	private IBpmRuntimeTaskService taskService;
	
	@Autowired
	private IBpmRuntimeService runtimeService;
	

	@Override
	protected void doAfterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doPostHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav) {
//		Object bpDataEx = vlService.getRequestAttribute(Constant.BPM_BP_DATA_EX_KEY);
//		if(bpDataEx != null){
//			mav.getModel().put(Constant.BPM_BP_DATA_EX_KEY,bpDataEx);
//		}
	}

	@Override
	protected boolean doPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		log.info("开始 BusinessProcessBeginInterceptor.preHandle 方法。");
		String taskId = request.getParameter(BpmConstant.BPM_TASK_ID_KEY);
		String bizKey = request.getParameter(BpmConstant.BPM_BIZ_KEY);
		IBaseEntity user = (IBaseEntity)vlService.getSessionAttribute(BpmConstant.BPM_LOGON_USER);
		if(StringUtils.isBlank(taskId) && StringUtils.isBlank(bizKey)){
			log.info("没有流程任务Id和BizKey，结束 BusinessProcessBeginInterceptor.preHandle 方法。");
			return true;
		}
		log.info("taskId:" + taskId);
		log.info("bizKey:" + bizKey);
		BaseBpDataEx bpInfoEx = null;
		if(!StringUtils.isBlank(taskId)){
			TaskEx taskEx = taskService.getTask(taskId, user.getId(),null);
	        if(taskEx == null){
	            log.error("不能取得TaskID[" + taskId + "],用户[" + user.getId() + "]相关的Task信息。");
	            log.info("结束 BusinessProcessBeginInterceptor.preHandle 方法。");
	            return true;
	        }
	        bpInfoEx = runtimeService.getBpDataEx(taskEx.getTask().getProcessInstanceId(), user.getId());
	        if(bpInfoEx != null){
				bpInfoEx.setTaskEx(taskEx);
			}
		}else{
			bpInfoEx = runtimeService.getBpDataExByBizKey(bizKey, user.getId());
		}
		
		if(bpInfoEx != null){
			vlService.setRequestAttribute(BpmConstant.BPM_BP_DATA_EX_KEY, bpInfoEx);
		}else{
			log.error("没有取得BaseBpInfoEx");
		}
		log.info("结束 BusinessProcessBeginInterceptor.preHandle 方法。");
		return true;
	}

	@Override
	protected int initOrder() {
		return 10;
	}

}
