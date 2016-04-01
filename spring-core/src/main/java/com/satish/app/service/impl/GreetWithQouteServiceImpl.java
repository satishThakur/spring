package com.satish.app.service.impl;

import com.satish.app.service.GreetWithQouteService;
import com.satish.app.service.GreetingService;
import com.satish.app.service.QouteService;

public class GreetWithQouteServiceImpl implements GreetWithQouteService{
	
	private GreetingService greetingService;
	
	private QouteService qouteService;
	
	
	public GreetWithQouteServiceImpl(GreetingService greetingService, QouteService qouteService) {
		super();
		this.greetingService = greetingService;
		this.qouteService = qouteService;
	}
	
	@Override
	public void greetWithQoute(){
		greetingService.sayGreetings();
		qouteService.qoute();
	}
	

}
