package com.rboomerang.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rboomerang.app.domain.Contact;
import com.rboomerang.app.repositories.ContactsRepository;
import com.rboomerang.app.services.ContactsService;

@Service
@Transactional
public class ContactsServiceImpl implements ContactsService{
	
	@Autowired
	private ContactsRepository contactRepository;
	
	public ContactsServiceImpl() {
	}
	
	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.getAllContacts();
	}

	@Override
	public List<Contact> queryContacts(String name) {
		List<Contact> result = new ArrayList<>();
		for(Contact contact : contactRepository.getAllContacts()){
			if(contact.getName().toLowerCase().contains(name.toLowerCase()) ||
					contact.getAddress().toLowerCase().contains(name.toLowerCase())){
				result.add(contact);
			}
		}
		return result;
	}
	
	@Override
	public List<Contact> queryAllContacts(List<String> names) {
		return contactRepository.getFilteredContacts(names);
	}

	@Override
	public Contact getContactById(long id) {
		return contactRepository.getContactById(id);
	}

}
