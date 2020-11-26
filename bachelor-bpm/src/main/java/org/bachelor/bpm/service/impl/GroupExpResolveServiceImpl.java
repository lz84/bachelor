package org.bachelor.bpm.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.service.IBpmHistoryService;
import org.bachelor.bpm.service.IBpmRuntimeService;
import org.bachelor.bpm.service.IExpressionResolver;
import org.bachelor.bpm.service.IGroupExpResolveService;
import org.bachelor.core.entity.IBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupExpResolveServiceImpl implements IGroupExpResolveService {

	@Autowired
	private IExpressionResolver resolver;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public List<? extends IBaseEntity> resolve(String groupOrgExp, BaseBpDataEx bpDataEx) {
		return resolver.resolveUsersByGroupExp(groupOrgExp);
	}
}
