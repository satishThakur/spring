package com.satish.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.satish.app.domain.Contact;
import com.satish.app.services.ContactsService;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/contacts")
public class ContactsController {
	
	@Autowired
	private ContactsService contactsService;
	
	@RequestMapping(method=GET)
	public String queryContacts(@RequestParam(value="q", defaultValue="") String queryString, Model model){
		List<Contact> contacts = null;
		if(queryString != null && !queryString.isEmpty()){
			contacts = contactsService.queryContacts(queryString);
		}else{
			contacts = contactsService.getAllContacts();
		}
		
		model.addAttribute("contacts", contacts);
		return "contacts";
	}

}
