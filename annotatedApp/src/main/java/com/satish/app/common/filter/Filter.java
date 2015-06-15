package com.satish.app.common.filter;

public interface Filter<T>{
	
	public boolean isAllowed(T value);

}
