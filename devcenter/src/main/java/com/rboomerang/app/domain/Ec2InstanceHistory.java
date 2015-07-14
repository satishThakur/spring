package com.rboomerang.app.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.rboomerang.app.util.TimeUtils;

@Entity
public class Ec2InstanceHistory {
	
	private long id;
	
	private Date date;
	
	private String instanceId;
	
	public Ec2InstanceHistory(){

	}
	
	public Ec2InstanceHistory(EC2Instance instance){
		setInstanceId(instance.getInstanceId());
		date = TimeUtils.getCurrentDateWithoutTime();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Ec2InstanceHistory))
			return false;
		Ec2InstanceHistory other = (Ec2InstanceHistory)obj;
		return Objects.equals(this.instanceId, other.instanceId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(instanceId);
	}
	

}
