package com.satish.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.satish.app.dao.QouteDao;
import com.satish.app.service.QouteService;

@Service("QouteService")
public class QouteServiceImpl implements QouteService{
	
	private QouteDao qouteDao;
	
	
	
	private String prefix;
	
	@Autowired
	public QouteServiceImpl(QouteDao dao, @Value("${prefix}") String prefix) {
		qouteDao = dao;
		
		this.prefix = prefix; 
	}
	
	
	@Override
	public void qoute(){
		System.out.println(prefix + qouteDao.getQoute());
	}

}
