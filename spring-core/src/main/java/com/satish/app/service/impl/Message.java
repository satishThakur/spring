package com.satish.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("message")
public class Message {
	
	private String message;
	
	@Autowired
	public Message(@Value("#{systemProperties['greetings']}") String message, @Value("${greetings}") String defMessage){
		this.message = message != null ? message : defMessage;
	}
	
	public String getMessage(){
		return message;
	}

}
