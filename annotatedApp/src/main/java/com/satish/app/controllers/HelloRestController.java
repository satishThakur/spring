package com.satish.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.satish.app.model.Greeting;

@RestController
@RequestMapping("/sayHello")
public class HelloRestController {

	
	@RequestMapping(method=RequestMethod.GET, produces="application/json")
	public List<Greeting> sayHello(){
		List<Greeting> greetings = new ArrayList<>();
		greetings.add(new Greeting("Jackson", "Hello from JSON world!!"));
		greetings.add(new Greeting("Madhav", "Hey there"));
		greetings.add(new Greeting("Satish", "nknnsvlksd"));
		greetings.add(new Greeting("Jackson", "Hello from JSON world!!"));
		return greetings;
	}
}
