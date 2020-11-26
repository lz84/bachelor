/*
 * @(#)GenericDaoImpl.java	Mar 5, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.bachelor.context.service.IVLService;
import org.bachelor.dao.DaoConstant;
import org.bachelor.dao.IGenericDao;
import org.bachelor.dao.vo.PageVo;

/**
 * @author
 *
 */
@SuppressWarnings("unchecked")
public class GenericDaoImpl<T, ID extends Serializable> implements IGenericDao<T, ID> {

	private Log log = LogFactory.getLog(this.getClass());

	private Class<T> persistentClass;
	
	@Autowired(required=false)
	private JdbcTemplate jdbcTemplate;
	
	@Autowired(required=false)
	private SessionFactory sessionFactory;
	
	protected void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	protected void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	private IVLService vlService;
	
	public GenericDaoImpl(){
		//this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public Class<T> getPersistentClass() {
		if(persistentClass == null){
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return persistentClass;
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.bachelor.dao.IGenericDao#findById(java.io.Serializable, boolean)
	 */
	@Override
	public T findById(ID id) {
		Object obj = sessionFactory.getCurrentSession().get(getPersistentClass(), id);
		T t = (T)obj;
		return t;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.bachelor.dao.IGenericDao#findAll()
	 */
	@Override
	public List<T> findAll() {
		DetachedCriteria dc = getDetachedCriteria();
		return findByCriteria(dc);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.dao.IGenericDao#save(java.lang.Object)
	 */
	@Override
	public void save(T entity) {
		sessionFactory.getCurrentSession().persist(entity);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.dao.IGenericDao#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.dao.IGenericDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(T entity) {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	/* (non-Javadoc)
	 * @see org.bachelor.dao.IGenericDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	protected List<T> findByHQL(String hql) {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		PageVo pageVo = (PageVo)vlService.getRequestAttribute(DaoConstant.PAGE_INFO);
		if(pageVo == null){
			log.info("没有分页信息，查询全部记录。");
			return query.list();	
		}
		//取得全部记录数
		int count = query.list().size();
		pageVo.setCount(count);
		//计算全部页数
		int pageCount = count/pageVo.getPageRowNum();
		if(pageCount*pageVo.getPageRowNum() <= count){
			pageCount++;
		}
		
		int startIndex = pageVo.getStartIndex();
		int endIndex = pageVo.getEndIndex();
		log.info("分页开始位置：" + startIndex);
		log.info("分页结束位置：" + endIndex);
		if(endIndex <= startIndex || endIndex <= 0){
			return query.list();
		}
		//设置全部页数		
		pageVo.setPageCount(pageCount);
		//设置当前页数
		if(startIndex <= 0){
			pageVo.setPage(0);
		}else{
			int page = startIndex/pageVo.getPageRowNum();
			pageVo.setPage(page);
		}
		query.setFirstResult(pageVo.getStartIndex());
		query.setMaxResults(pageVo.getPageRowNum());
		return query.list();
	}
	
	protected List<T> findNoPageByHQL(String hql){
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public boolean exist(String propertyName, Object propertyValue){
		DetachedCriteria dc = getDetachedCriteria();
		dc.add(Restrictions.eq(propertyName, propertyValue));
		Criteria  criteria = dc.getExecutableCriteria(getSession());
		int count = Integer.valueOf(((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
		return (count > 0 ? true : false);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	protected DetachedCriteria getDetachedCriteria() {
		DetachedCriteria  dc = DetachedCriteria.forClass(getPersistentClass());
		return dc;
	}
	
	protected Criteria getCriteria() {
		Criteria  c = getSession().createCriteria(getPersistentClass());
		return c;
	}
	
	@Override
	public void merge(T t){
		getSession().merge(t);
	}
	
	protected List<T> findByCriteriaNoPage(DetachedCriteria dc) {
		Criteria  criteria = dc.getExecutableCriteria(getSession());
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		return criteria.list();	
	}
	
	protected List<T> findByCriteria(DetachedCriteria dc) {
		Criteria  criteria = dc.getExecutableCriteria(getSession());
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		PageVo pageVo = (PageVo)vlService.getRequestAttribute(DaoConstant.PAGE_INFO);
		if(pageVo == null){
			log.info("没有分页信息，查询全部记录。");
			return criteria.list();	
		}
		//取得全部记录数
		long count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult());
		criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		pageVo.setCount(count);
		//计算全部页数
		long pageCount = count/pageVo.getPageRowNum();
		if(pageCount*pageVo.getPageRowNum() <= count){
			pageCount++;
		}
		
		int startIndex = pageVo.getStartIndex();
		int limitIndex = pageVo.getEndIndex();
		log.info("分页开始位置：" + startIndex);
		log.info("分页结束位置：" + limitIndex);
		log.info("分页参数不正确，查询全部记录。");
		if(limitIndex <= startIndex || limitIndex <= 0){
			return criteria.list();
		}
		//设置全部页数		
		pageVo.setPageCount(pageCount);
		//设置当前页数
		if(startIndex <= 0){
			pageVo.setPage(0);
		}else{
			int page = startIndex/pageVo.getPageRowNum();
			pageVo.setPage(page);
		}
		List<T> list = criteria.setFirstResult(pageVo.getStartIndex()).setMaxResults(pageVo.getPageRowNum()).list();
		return list;
	}

	/* (non-Javadoc)
	 * @see org.bachelor.dao.IGenericDao#findByProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		DetachedCriteria  dc = getDetachedCriteria();
		dc.add(Restrictions.eq(propertyName, propertyValue));
		return findByCriteria(dc);
	}
	
	protected int executeHql(String hql){
		int num = sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
		return num;
	}
	
	protected String pageSql(String sql){
		PageVo pageVo = (PageVo)vlService.getRequestAttribute(DaoConstant.PAGE_INFO);
		if(pageVo == null){
			log.info("没有分页信息，查询全部记录。");
			return sql;	
		}
		//取得全部记录数
		String countSql = "select count(*) from (" + sql + ")";
		long count = getJdbcTemplate().queryForLong(countSql);
		pageVo.setCount(count);
		//计算全部页数
		long pageCount = count/pageVo.getPageRowNum();
		if(pageCount*pageVo.getPageRowNum() <= count){
			pageCount++;
		}
		
		int startIndex = pageVo.getStartIndex();
		int endIndex = pageVo.getEndIndex();
		log.info("分页开始位置：" + startIndex);
		log.info("分页结束位置：" + endIndex);
		if(endIndex <= startIndex || endIndex <= 0){
			return sql;
		}
		//设置全部页数		
		pageVo.setPageCount(pageCount);
		//设置当前页数
		if(startIndex <= 0){
			pageVo.setPage(0);
		}else{
			int page = startIndex/pageVo.getPageRowNum();
			pageVo.setPage(page);
		}
		
		String pageSql = "select * from(select tmp_a.*, rownum rn from (" + sql + ") tmp_a where rownum <= " + endIndex + ") where rn > " + startIndex;
		return pageSql;
	}

	@Override
	public void evict(T entity) {
		getSession().evict(entity);
	}
}
