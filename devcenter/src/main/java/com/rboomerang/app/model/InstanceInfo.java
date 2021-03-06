package com.rboomerang.app.model;

import com.rboomerang.app.domain.Client;
import com.rboomerang.app.domain.EC2Instance;
import com.rboomerang.app.domain.ElbInstance;
import com.rboomerang.app.domain.Environment;
import com.rboomerang.app.domain.ISystem;
import com.rboomerang.app.domain.RdsInstance;
import com.rboomerang.app.domain.S3Bucket;

public class InstanceInfo {
	
	private Client client;
	
	private ISystem system;
	
	private Environment env;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ISystem getSystem() {
		return system;
	}

	public void setSystem(ISystem system) {
		this.system = system;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}
	
	
	/**
	 * Factory methods..
	 * @param ec2Instance
	 * @return
	 */
	
	public static InstanceInfo getInstanceInfo(EC2Instance ec2Instance){
		InstanceInfo info = new InstanceInfo();
		info.setClient(new Client(ec2Instance.getClient()));
		info.setEnv(new Environment(ec2Instance.getEnv()));
		info.setSystem(new ISystem(ec2Instance.getSystem()));
		return info;
	}
	
	public static InstanceInfo getInstanceInfo(RdsInstance rdsInstance){
		InstanceInfo info = new InstanceInfo();
		info.setClient(new Client(rdsInstance.getClient()));
		info.setEnv(new Environment(rdsInstance.getEnv()));
		info.setSystem(new ISystem(rdsInstance.getSystem()));
		return info;
	}
	
	public static InstanceInfo getInstanceInfo(ElbInstance elbInstance){
		InstanceInfo info = new InstanceInfo();
		info.setClient(new Client(elbInstance.getClient()));
		info.setEnv(new Environment(elbInstance.getEnv()));
		info.setSystem(new ISystem(elbInstance.getSystem()));
		return info;
	}
	public static InstanceInfo getInstanceInfo(S3Bucket s3Bucket){
		InstanceInfo info = new InstanceInfo();
		info.setClient(new Client(s3Bucket.getClient()));
		info.setEnv(new Environment(s3Bucket.getEnv()));
		info.setSystem(new ISystem(s3Bucket.getSystem()));
		return info;
	}


}
