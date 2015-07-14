package com.rboomerang.app.domain;

import java.util.Objects;

public class Client {
	
	private String name;
	
	public Client(){
		
	}
	
	public Client(String name){
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Client: " + name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Client))
			return false;
		Client other = (Client)obj;
		return Objects.equals(name, other.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

}
