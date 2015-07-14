package com.rboomerang.app.repositories;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rboomerang.app.domain.Contact;

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
	
	@SuppressWarnings("unchecked")
	public List<Contact> getFilteredContacts(List<String> names){
		
		System.out.println("Inside getFilteredContacts: " + names);
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contact.class);
		Disjunction disjunction = Restrictions.disjunction();
		for(String name : names){
			System.out.println("Adding" + name);
			disjunction.add(Restrictions.or(Restrictions.like("name", "%" +name + "%")));
			
		}
		
		criteria.add(disjunction);
	    
		return criteria.list();	
		
	}
	

}
