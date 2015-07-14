package com.rboomerang.app.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class S3BucketHistory {
	
	private long id;
	
	private String bucketName;
	
	private Date date;
	
	public S3BucketHistory() {
		
	}
	
	
	public S3BucketHistory(S3Bucket bucket){
		this.bucketName = bucket.getBucketName();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
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
		if(! (obj instanceof S3BucketHistory))
			return false;
		S3BucketHistory other = (S3BucketHistory)obj;
		return Objects.equals(bucketName, other.bucketName) &&
				Objects.equals(date, other.date);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bucketName, date);
	}

}
