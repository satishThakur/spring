package com.rboomerang.app.common.converters;

import com.rboomerang.app.domain.Environment;

public class EnvConverter implements Converter<String, Environment>{

	@Override
	public Environment convert(String env) {
		return new Environment(env);
	}

}
