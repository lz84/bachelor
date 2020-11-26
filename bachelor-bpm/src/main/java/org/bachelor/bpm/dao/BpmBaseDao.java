/*
 * @(#)GenericDaoImpl.java	Mar 5, 2013
 *
 * Copyright (c) 2013, Team Bachelor. All rights reserved.
 */
package org.bachelor.bpm.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.bachelor.dao.impl.GenericDaoImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author
 *
 */
public class BpmBaseDao<T, ID extends Serializable> extends GenericDaoImpl<T, ID> {
	
	@Autowired(required=false)
	private JdbcTemplate pdJdbcTemplate;
	
	@Autowired(required=false)
	private SessionFactory pdSessionFactory; 

	@PostConstruct
	protected void init() {
		if(this.pdJdbcTemplate != null){
			setJdbcTemplate(pdJdbcTemplate);
		}
		if(this.pdSessionFactory != null){
			setSessionFactory(pdSessionFactory);
		}
	}
}
