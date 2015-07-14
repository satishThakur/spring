package com.rboomerang.app.common.filter;

public interface Filter<T>{
	
	public boolean isAllowed(T value);

}
