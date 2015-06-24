package com.satish.app.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EC2Instance {
	
	private long id;
	
	private String instanceId;
	
	private String region;
	
	private String instanceType;
	
	private boolean isDead;
	
	private Date deadDate;
	
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
	
	@Column(name = "instanceId",unique=true)
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getInstanceType() {
		return instanceType;
	}

	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	@Column(name = "dead", columnDefinition = "boolean default false", nullable = false)
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public Date getDeadDate() {
		return deadDate;
	}

	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
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
		if(! (obj instanceof EC2Instance))
			return false;
		EC2Instance other = (EC2Instance)obj;
		return instanceId.equals(other.instanceId);
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(instanceId);
	}

	

}
