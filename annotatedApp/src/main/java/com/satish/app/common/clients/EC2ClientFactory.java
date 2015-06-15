package com.satish.app.common.clients;

import com.amazonaws.regions.Region;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;

public class EC2ClientFactory {
	
	public static AmazonEC2 getEc2Client(Region region){
		AmazonEC2 client = new AmazonEC2Client();
		client.setRegion(region);
		return client;
	}
}
