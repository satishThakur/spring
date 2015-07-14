package com.rboomerang.app.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ElbInstanceHistory {
	
	private long id;

	private String elbName;
	
	private Date date;
	
	public ElbInstanceHistory(){
		
	}

	public ElbInstanceHistory(ElbInstance instance) {
		setElbName(instance.getElbName());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getElbName() {
		return elbName;
	}

	public void setElbName(String elbName) {
		this.elbName = elbName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(! (obj instanceof ElbInstanceHistory))
			return false;
		ElbInstanceHistory that = (ElbInstanceHistory)obj;
		
		return Objects.equals(elbName, that.elbName);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(elbName);
	}	

}
