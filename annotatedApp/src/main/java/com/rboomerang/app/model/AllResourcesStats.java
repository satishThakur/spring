package com.rboomerang.app.model;

public class AllResourcesStats {
	
	private int ec2InstancesCount;
	
	private int rdsInstancesCount;
	
	private int elbInstancesCount;
	
	private int s3InstancesCount;

	public int getEc2InstancesCount() {
		return ec2InstancesCount;
	}

	public void setEc2InstancesCount(int ec2InstancesCount) {
		this.ec2InstancesCount = ec2InstancesCount;
	}

	public int getRdsInstancesCount() {
		return rdsInstancesCount;
	}

	public void setRdsInstancesCount(int rdsInstancesCount) {
		this.rdsInstancesCount = rdsInstancesCount;
	}

	public int getElbInstancesCount() {
		return elbInstancesCount;
	}

	public void setElbInstancesCount(int elbInstancesCount) {
		this.elbInstancesCount = elbInstancesCount;
	}

	public int getS3InstancesCount() {
		return s3InstancesCount;
	}

	public void setS3InstancesCount(int s3InstancesCount) {
		this.s3InstancesCount = s3InstancesCount;
	}
	
	

}
