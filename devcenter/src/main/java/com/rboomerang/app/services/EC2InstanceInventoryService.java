package com.rboomerang.app.services;

import java.util.Date;

public interface EC2InstanceInventoryService {

	void sync(Date syncDate);

}
