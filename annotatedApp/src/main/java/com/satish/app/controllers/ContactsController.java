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
	public String queryContacts(@RequestParam(value="q", defaultValue="") List<String> queryStrings, Model model){
		System.out.println("Query String: " + queryStrings);
		List<Contact> contacts = null;
		if(queryStrings != null && !queryStrings.isEmpty()){
			System.out.println("Querying filtered names");
			contacts = contactsService.queryAllContacts(queryStrings);
		}else{
			contacts = contactsService.getAllContacts();
		}
		
		model.addAttribute("contacts", contacts);
		return "contacts";
	}

}
