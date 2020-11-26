/*
 * @(#)IGenericDao.java	Mar 5, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author
 *
 */
public interface IGenericDao<T, ID extends Serializable> {

	T findById(ID id);
	 
    List<T> findAll();
 
    void save(T entity);
    
    void update(T entity);
    
    void saveOrUpdate(T entity);
 
    void delete(T entity);
    
    public JdbcTemplate getJdbcTemplate() ;
    
    public List<T> findByProperty(String propertyName, Object propertyValue);
    
    public boolean exist(String propertyName, Object propertyValue);
    
    public void evict(T entity);
    
    public void merge(T t);

    
}
