package org.bachelor.bpm.listener;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.bachelor.bpm.domain.BaseBpDataEx;

@Service
public class SubProcessStartListener implements ExecutionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Map<String, Object> pv = new HashMap<String, Object>();
		BaseBpDataEx subVariable = (BaseBpDataEx) execution
				.getVariable("subBpDateEx");
		this.copyPropertiesToParent(subVariable);
		addToGvMap(subVariable, pv);
		Map<String, Object> businessExtMap = subVariable.getBusinessExtMap();
		addBunissMapToGv(businessExtMap, pv);
		execution.setVariables(pv);

	}

	private void addToGvMap(Object bpDataEx, Map<String, Object> pv) {
		try {
			Map map = PropertyUtils.describe(bpDataEx);
			for (Object keyObj : map.keySet()) {
				if (!(keyObj instanceof String)) {
					continue;
				}
				String key = keyObj.toString();
				if (key.equals("taskEx")) {
					continue;
				}
				if (key.equals("class")) {
					continue;
				}
				Object valueObj = map.get(key);

				if (valueObj instanceof Map) {
					continue;
				}
				pv.put(key, valueObj);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void addBunissMapToGv(Map<String, Object> businessExtMap,
			Map<String, Object> pv) {
		for (Object keyObj : businessExtMap.keySet()) {
			if (!(keyObj instanceof String)) {
				continue;
			}
			String key = keyObj.toString();
			pv.put(key, businessExtMap.get(key));
		}
	}
	
	public static BaseBpDataEx copyPropertiesToParent(
			BaseBpDataEx childBean) {
		try {
			
			Map<String, Object> businessExtMap = childBean.getBusinessExtMap();
			
			if(businessExtMap != null){
				businessExtMap = new HashMap<String, Object>();
			}
			
			businessExtMap = PropertyUtils.describe(childBean);
			List<String> list=(List<String>) businessExtMap.get("dwbzList");
			
			businessExtMap.remove("class");
			
			Field[] fields = BaseBpDataEx.class.getDeclaredFields();
			
			for(Field f : fields){
				if(businessExtMap.keySet().size() > 0 && businessExtMap.keySet().contains(f.getName())){
					businessExtMap.remove(f.getName());
				}
			}
			
			childBean.setBusinessExtMap(businessExtMap);
			
		} catch (Exception e) {
		}
		
		return childBean;
	}
	
	
	
}
