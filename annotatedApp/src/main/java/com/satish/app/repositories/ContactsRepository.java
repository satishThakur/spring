package com.satish.app.repositories;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.satish.app.domain.Contact;

@Repository
public class ContactsRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void saveContact(Contact contact){
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(Contact.class.getName(), contact);
	}
	
	@SuppressWarnings("unchecked")
	public List<Contact> getAllContacts(){
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria =  session.createCriteria(Contact.class);
		return criteria.list();
	}
	
	public Contact getContactById(long id){
		Session session = sessionFactory.getCurrentSession();
		return (Contact) session.get(Contact.class, id);
	}
	

}
