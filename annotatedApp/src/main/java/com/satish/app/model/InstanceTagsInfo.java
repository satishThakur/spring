package com.satish.app.model;

import java.util.List;

public class InstanceTagsInfo<T> {
	
	private T instance;
	
	private List<String> invalidTags;
	
	
	public T getInstance() {
		return instance;
	}

	public void setInstance(T instance) {
		this.instance = instance;
	}
		
	public List<String> getInvalidTags() {
		return invalidTags;
	}

	public void setInvalidTags(List<String> invalidTags) {
		this.invalidTags = invalidTags;
	}	

}
