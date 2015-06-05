package com.satish.app.model;

public class RdsInstance {

	private String endPoint;

	private String DBInstanceClass;

	private String region;

	private String client;

	private String system;

	private String env;

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getDBInstanceClass() {
		return DBInstanceClass;
	}

	public void setDBInstanceClass(String dBInstanceClass) {
		DBInstanceClass = dBInstanceClass;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}
