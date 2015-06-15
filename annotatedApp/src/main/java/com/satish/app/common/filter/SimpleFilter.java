package com.satish.app.common.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleFilter<T> implements Filter<T>{
	
	private List<T> allowedValues = new ArrayList<>();
		
	public SimpleFilter(){
		this(Collections.emptyList());
	}

	public SimpleFilter(List<T> valuesAllowed) {
		allowedValues.addAll(valuesAllowed);
	}
	
	public void addAllowedValues(T value){
		allowedValues.remove(value);
	}
	
	public void addAllowedValue(T value){
		allowedValues.add(value);
	}

	@Override
	public boolean isAllowed(T value) {
		return allowedValues.contains(value);
	}

}
