package com.rboomerang.app.common.converters;

import com.rboomerang.app.domain.ISystem;

public class ISystemConverter implements Converter<String, ISystem>{

	@Override
	public ISystem convert(String systemName) {
		return new ISystem(systemName);
	}

}
