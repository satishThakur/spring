package com.satish.app.services;

import java.util.List;

import com.satish.app.domain.Contact;

public interface ContactsService {
	
	public List<Contact> getAllContacts();
	
	public List<Contact> queryContacts(String query);
	
	public Contact getContactById(long id);

	List<Contact> queryAllContacts(List<String> names);
	

}
