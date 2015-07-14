package com.rboomerang.app.model;

public class User {
	
	private String name;
	private String email;
	
	public User(String name, String email){
		this.setName(name);
		this.setEmail(email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
