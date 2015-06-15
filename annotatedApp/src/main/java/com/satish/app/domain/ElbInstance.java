package com.satish.app.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ElbInstance {
	
	private long id;

	private String elbName;
	
	private String region;
	
	private String client;

	private String system;

	private String env;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "elbName",unique=true)
	public String getElbName() {
		return elbName;
	}

	public void setElbName(String elbName) {
		this.elbName = elbName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(! (obj instanceof ElbInstance))
			return false;
		ElbInstance that = (ElbInstance)obj;
		
		return Objects.equals(elbName, that.elbName);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(elbName);
	}	

}
