package com.satish.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.satish.app.model.User;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("user", new User("Satish", "satish.manit@gmail.com"));
		return "home";
	}
}
