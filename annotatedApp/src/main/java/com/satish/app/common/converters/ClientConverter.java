package com.satish.app.common.converters;

import com.satish.app.domain.Client;

public class ClientConverter implements Converter<String, Client> {

	@Override
	public Client convert(String clientName) {
		
		Client client =  new Client();
		client.setName(clientName);
		return client;
	}

}
