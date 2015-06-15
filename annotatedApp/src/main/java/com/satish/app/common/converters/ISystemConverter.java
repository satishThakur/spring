package com.satish.app.common.converters;

import com.satish.app.domain.ISystem;

public class ISystemConverter implements Converter<String, ISystem>{

	@Override
	public ISystem convert(String systemName) {
		return new ISystem(systemName);
	}

}
