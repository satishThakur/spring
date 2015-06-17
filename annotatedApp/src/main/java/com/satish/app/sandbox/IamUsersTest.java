package com.satish.app.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.cloudwatch.model.Statistic;

public class IamUsersTest {

	
	public static void main(String[] args){		
		
		
		/**
		 * Subcribe an alarm example.
		 */			
		
		
		AmazonCloudWatchClient cwClient = new AmazonCloudWatchClient();
		cwClient.setRegion(Region.getRegion(Regions.US_WEST_2));
		PutMetricAlarmRequest req = new PutMetricAlarmRequest();
		
		req.setAlarmName("DiskSpaceExceededAlarm");
		req.setAlarmDescription("Alarm when dsk exceeds 70 percent");
		req.setMetricName("DiskSpaceUtilization");
		req.setNamespace("AWS/EC2");
		req.setThreshold(10.0);
		req.setStatistic(Statistic.Average);
		req.setPeriod(300);
		req.setComparisonOperator(ComparisonOperator.GreaterThanOrEqualToThreshold);
		Collection<Dimension> dimentions = new ArrayList<>();
		Dimension d = new Dimension();
		d.setName("InstanceId");
		d.setValue("i-ecfdb71b");
		
		dimentions.add(d);
		
		req.setDimensions(dimentions);
		
		req.setEvaluationPeriods(2);
		
		req.setAlarmActions(Arrays.asList("arn:aws:sns:us-west-2:208876916689:InfraTest"));
		req.setUnit(StandardUnit.Percent);
		
		cwClient.putMetricAlarm(req);
		
	}
}
