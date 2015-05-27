package com.satish.fitnesstracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	public HelloController() {
		System.out.println("Controller created...!!");
	}
	
	@RequestMapping(value="/greetings")
	public String sayHello(Model model){
		model.addAttribute("greeting", "hello world!!");
		return "hello";
	}

}
