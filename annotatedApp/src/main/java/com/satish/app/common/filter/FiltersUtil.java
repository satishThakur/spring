package com.satish.app.common.filter;

public class FiltersUtil {
	
	public <T> Filter<T> or(Filter<T> filter1, Filter<T> filter2){
		return new Filter<T>() {

			@Override
			public boolean isAllowed(T value) {
				return filter1.isAllowed(value) || filter2.isAllowed(value);
			}
		};
	}
	
	public <T> Filter<T> and(Filter<T> filter1, Filter<T> filter2){
		
		return new Filter<T>() {

			@Override
			public boolean isAllowed(T value) {
				
				return filter1.isAllowed(value) && filter2.isAllowed(value);
			}
		};
	}
}
