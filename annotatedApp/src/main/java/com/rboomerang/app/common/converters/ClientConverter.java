package com.rboomerang.app.common.converters;

import com.rboomerang.app.domain.Client;

public class ClientConverter implements Converter<String, Client> {

	@Override
	public Client convert(String clientName) {
		
		Client client =  new Client();
		client.setName(clientName);
		return client;
	}

}
