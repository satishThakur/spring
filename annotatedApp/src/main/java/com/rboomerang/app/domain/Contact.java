package com.rboomerang.app.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact {
	
	private long id;
	
	private String name;
	
	private String address;
	
	public Contact(){
		
	}
	
	public Contact(long id,String name, String address){
		this.setId(id);
		this.setName(name);
		this.setAddress(address);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name,address);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Contact))
			return false;
		Contact other = (Contact)obj;
		return Objects.equals(name, other.name) && Objects.equals(address, other.address);
	}
	
	@Override
	public String toString() {
		return name + ":" + address;
	}

}
