package org.bachelor.bpm.common;

import java.util.Map;

import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.domain.BaseBpDataEx;

public class BpmUtils {

	private static Log log = LogFactory.getLog(BpmUtils.class);
	
	private static final String PRE_STR="\"";
	private static final String END_STR="\"";

//	public static String resolveGroupExp(RuntimeService runtimeService,
//			String groupExp, String processInstanceId) {
//
//		String resolvedGroupName = null;
//		String groupId = getVariableName(StringUtils.substringBeforeLast(
//				groupExp, "#"));
//		String orgVarName = getVariableName(StringUtils.substringAfterLast(
//				groupExp, "#"));
//		String orgValue = "";
//		String groupValue="";
//		BaseBpDataEx bpDataEx = (BaseBpDataEx) runtimeService.getVariable(
//				processInstanceId, Constant.BPM_BP_DATA_EX_KEY);
//		if (processInstanceId != null) {
//			try {
//				orgValue = BeanUtils.getProperty(bpDataEx, orgVarName);
//				groupValue = BeanUtils.getProperty(bpDataEx, groupId);
//				resolvedGroupName = groupValue + "#" + orgValue;
//			} catch (Exception e) {
//				log.error("解析待办人出错，表达式：" + groupExp, e);
//			}
//		} else {
//			orgValue = orgVarName;
//			resolvedGroupName = groupId + "#" + orgValue;
//		}
//		return resolvedGroupName;
//	}
	@Deprecated
	public static boolean isCandidateGroup(IdentityLink il) {
		if (!StringUtils.isEmpty(il.getGroupId())
				&& il.getType().equals(IdentityLinkType.CANDIDATE)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Deprecated
	public static boolean haveOrgInfo(String groupId) {
		return StringUtils.contains(groupId, "#");
	}

	private static String getVariableName(String variableStr) {
//		String variableName = StringUtils.substringBeforeLast(
//				(StringUtils.substringAfterLast(variableStr, "${")), "}");
		return variableStr;

	}
	public static <T extends BaseBpDataEx> void copyPropertiesFormBase(
			T childBean, BaseBpDataEx bean) {
		try {
//			childBean.setDomainId(bean.getDomainId());
//			childBean.setPiId(bean.getPiId());
//			childBean.setTaskEx(bean.getTaskEx());
//			childBean.setBusinessExtMap(bean.getBusinessExtMap());
			BeanUtils.copyProperties(childBean, bean);
			Map<String, Object> businessExtMap = bean.getBusinessExtMap();
			if (businessExtMap != null) {
				for (String key : businessExtMap.keySet()) {
					BeanUtils.setProperty(childBean, key,
							businessExtMap.get(key));
				}
			}
		} catch (Exception e) {
		}
	}
	public static String toTaskReviewCandidate(String userId){
		return new StringBuilder("|").append(userId).append('|').toString();
	}
}
