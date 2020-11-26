package org.bachelor.bpm.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bachelor.bpm.common.BpmUtils;
import org.bachelor.bpm.dao.BpmBaseDao;
import org.bachelor.bpm.dao.IBpmTaskReviewDao;
import org.bachelor.bpm.domain.BaseBpDataEx;
import org.bachelor.bpm.domain.BpmTaskReview;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BpmTaskReviewDao extends BpmBaseDao<BpmTaskReview, String>
		implements IBpmTaskReviewDao {

	@Override
	public List<BpmTaskReview> getTaskReviewsByTaskId(String taskId) {
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.eq("reviewTaskId", taskId)).addOrder(
				Order.asc("reviewDate"));
		List<BpmTaskReview> reviews = findByCriteria(dc);
		return reviews;
	}

	@Override
	public List<BpmTaskReview> getTaskReviewsByBizKey(String bizKey) {
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.eq("bizKey", bizKey)).addOrder(
				Order.desc("reviewDate"));
		List<BpmTaskReview> reviews = findByCriteriaNoPage(dc);
		return reviews;
	}
	
	@Override
	public List<BpmTaskReview> findByTaskDefinitionKey(String key) {
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.eq("reviewTaskDefKey", key)).addOrder(
				Order.desc("reviewDate"));
		List<BpmTaskReview> reviews = findByCriteriaNoPage(dc);
		return reviews; 
	}


	@Override
	public List<BpmTaskReview> getWaitingTasks(String userId) {
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.like("candUserId", 
				BpmUtils.toTaskReviewCandidate(userId), MatchMode.ANYWHERE))
					.addOrder(Order.desc("reviewDate"));
		dc.add(Restrictions.eq("isTaskFinish", "0"));
		List<BpmTaskReview> reviews = findByCriteriaNoPage(dc);
		return reviews;
	}

	@Override
	public List<BpmTaskReview> getFinishedTasks(String userId) {
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.eq("reviewUserId", userId)).addOrder(
				Order.desc("reviewDate"));
		dc.add(Restrictions.eq("isTaskFinish", "1"));
		List<BpmTaskReview> reviews = findByCriteriaNoPage(dc);
		return reviews;
	}
	
	private BaseBpDataEx getBpExData(String piid) {
		JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
//		String sql = "select bytes_ from ACT_RU_VARIABLE t join ACT_GE_BYTEARRAY b on t.bytearray_id_ = b.id_ where t.proc_inst_id_ = '"
//				+ piid + "' and t.name_ = '" + Constant.BPM_BP_DATA_EX_KEY + "'";
       String  sql="select * from ACT_GE_BYTEARRAY t where t.id_='195240'";
		List<Map<String, Object>> l = jdbcTemplate.queryForList(sql);
		byte[] bytes = (byte[]) l.get(0).get("bytes_");
		ObjectInputStream ois=null;
		ObjectOutputStream oos=null;
		try {
			ois=new ObjectInputStream(new ByteArrayInputStream(bytes));
			List assigneList=(List) ois.readObject();
			ois.close();
			List test=new ArrayList();
			test.add("a");
			test.add("b");
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		try {
//			//DevManBpDataEx b = (DevManBpDataEx)new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject(); 
////			return bpex; 
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 return null;
	}

}
