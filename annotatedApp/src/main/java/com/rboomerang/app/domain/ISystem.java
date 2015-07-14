package com.rboomerang.app.domain;

import java.util.Objects;

public class ISystem {
	
	private String name;
	
	public ISystem(String name){
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
		if(!(obj instanceof ISystem))
			return false;
		ISystem other = (ISystem)obj;
		return Objects.equals(name, other.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}


}
