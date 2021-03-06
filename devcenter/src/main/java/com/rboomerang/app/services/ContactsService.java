package com.rboomerang.app.services;

import java.util.List;

import com.rboomerang.app.domain.Contact;

public interface ContactsService {
	
	public List<Contact> getAllContacts();
	
	public List<Contact> queryContacts(String query);
	
	public Contact getContactById(long id);

	List<Contact> queryAllContacts(List<String> names);
	

}
