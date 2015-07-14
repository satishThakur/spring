package com.rboomerang.app.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "RdsInstance", 
uniqueConstraints = @UniqueConstraint(columnNames = {"instanceId", "region"}))
public class RdsInstance {
	
	private long id;
	
	//This is only unique per account per region.
	private String instanceId;

	private String endPoint;

	private String DBInstanceClass;

	private String region;

	private String client;

	private String system;

	private String env;
	
	private String own;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	


	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getDBInstanceClass() {
		return DBInstanceClass;
	}

	public void setDBInstanceClass(String dBInstanceClass) {
		DBInstanceClass = dBInstanceClass;
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
	
	public String getOwn() {
		return own;
	}

	public void setOwn(String own) {
		this.own = own;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof RdsInstance))
			return false;
		RdsInstance that = (RdsInstance)obj;

		return Objects.equals(this.instanceId, that.instanceId) && 
				Objects.equals(this.region, that.region);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(instanceId, region);
	}

	

}
