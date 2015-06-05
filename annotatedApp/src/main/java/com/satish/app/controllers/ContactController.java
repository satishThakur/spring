package com.satish.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.satish.app.domain.Contact;
import com.satish.app.services.ContactsService;

@Controller
@RequestMapping("/contacts/{contactId}")
public class ContactController {
	
	@Autowired
	private ContactsService contactsService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getContact(@PathVariable("contactId") long contactId, Model model){
		Contact contact = contactsService.getContactById(contactId);
		if(contact != null){
			System.out.println("Found contact " + contact);
			model.addAttribute("contact", contact);
		}
		
		return "contact";
	}
	

}
