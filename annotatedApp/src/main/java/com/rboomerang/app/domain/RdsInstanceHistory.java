package com.rboomerang.app.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RdsInstanceHistory {
	
	private long id;
	
	private String instanceId;
	
	private String region;
	
	private Date date;
	
	public RdsInstanceHistory(){
		
	}

	public RdsInstanceHistory(RdsInstance instance) {
		setInstanceId(instance.getInstanceId());
		setRegion(instance.getRegion());
	}

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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + " : " + instanceId + " : " + region + ": On" + date;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof RdsInstanceHistory))
			return false;
		RdsInstanceHistory other = (RdsInstanceHistory)obj;
		return Objects.equals(this.instanceId, other.instanceId) &&
				Objects.equals(this.region, other.region) &&
				Objects.equals(this.date, other.date);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(instanceId, region, date);
	}
	

}
