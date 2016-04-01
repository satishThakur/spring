package com.satish.app.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.satish.app.service.GreetingService;

public class GreetingServiceImpl implements GreetingService{
	
	@Autowired
	private Message message;
	
	@PostConstruct
	public void init(){
		System.out.println("Init called.... " + message);
	}
	
	public GreetingServiceImpl() {
		System.out.println("Message is: " + message);
	}

	@Override
	public void sayGreetings() {
		System.out.println( message.getMessage() + " there!!");
		
	}

}
