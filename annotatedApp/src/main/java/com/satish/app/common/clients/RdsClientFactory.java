package com.satish.app.common.clients;

import com.amazonaws.regions.Region;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClient;

public class RdsClientFactory {
	
	public static AmazonRDS getRdsClient(Region region){
		AmazonRDS rdsClient = new AmazonRDSClient();
		rdsClient.setRegion(region);
		return rdsClient;
	}

}
