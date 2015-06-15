package com.satish.app.common.converters;

import com.satish.app.domain.Environment;

public class EnvConverter implements Converter<String, Environment>{

	@Override
	public Environment convert(String env) {
		return new Environment(env);
	}

}
