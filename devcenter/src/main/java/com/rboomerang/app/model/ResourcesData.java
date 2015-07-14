package com.rboomerang.app.model;

import java.util.Date;

import com.rboomerang.app.util.TimeUtils;

public class ResourcesData {
	
	private RegionStats ec2Stats;
	
	private RegionStats rdsStats;
	
	private RegionStats elbStats;
	
	private RegionStats s3Stats;
	
	private Date date;
	
	public ResourcesData(){
		date = TimeUtils.getCurrentDateWithoutTime();
	}

	public RegionStats getEc2Stats() {
		return ec2Stats;
	}

	public void setEc2Stats(RegionStats ec2Stats) {
		this.ec2Stats = ec2Stats;
	}

	public RegionStats getRdsStats() {
		return rdsStats;
	}

	public void setRdsStats(RegionStats rdsStats) {
		this.rdsStats = rdsStats;
	}

	public RegionStats getElbStats() {
		return elbStats;
	}

	public void setElbStats(RegionStats elbStats) {
		this.elbStats = elbStats;
	}

	public RegionStats getS3Stats() {
		return s3Stats;
	}

	public void setS3Stats(RegionStats s3Stats) {
		this.s3Stats = s3Stats;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	
	

}
