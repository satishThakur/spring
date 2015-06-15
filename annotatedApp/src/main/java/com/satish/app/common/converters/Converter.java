package com.satish.app.common.converters;

public interface Converter<T,U> {
	public U convert(T t);
}
