package com.rboomerang.app.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.route53.AmazonRoute53Client;
import com.amazonaws.services.route53.model.Change;
import com.amazonaws.services.route53.model.ChangeAction;
import com.amazonaws.services.route53.model.ChangeBatch;
import com.amazonaws.services.route53.model.ChangeInfo;
import com.amazonaws.services.route53.model.ChangeResourceRecordSetsRequest;
import com.amazonaws.services.route53.model.ChangeResourceRecordSetsResult;
import com.amazonaws.services.route53.model.GetChangeRequest;
import com.amazonaws.services.route53.model.GetChangeResult;
import com.amazonaws.services.route53.model.ListResourceRecordSetsRequest;
import com.amazonaws.services.route53.model.ListResourceRecordSetsResult;
import com.amazonaws.services.route53.model.RRType;
import com.amazonaws.services.route53.model.ResourceRecord;
import com.amazonaws.services.route53.model.ResourceRecordSet;

public class Route53Sample {
	
	public static void listRecordSetsForHostedZone() {
		
		ListResourceRecordSetsRequest request = new ListResourceRecordSetsRequest();
		request.setHostedZoneId("ZHM7H6OKNEBJ4");
		
		AmazonRoute53Client client = new AmazonRoute53Client();
		ListResourceRecordSetsResult result = client.listResourceRecordSets(request);
		List<ResourceRecordSet> recordSets = result.getResourceRecordSets();
		
		for(ResourceRecordSet recordSet : recordSets) {
			
			System.out.println(recordSet.toString());
		}
	}
	
	
	
	public static void createOrUpdateARecord() throws Exception{
		AmazonRoute53Client client = new AmazonRoute53Client();
		List<ResourceRecord> records = new ArrayList<ResourceRecord>();
		ResourceRecord record = new ResourceRecord();
		record.setValue("52.4.67.6");
		records.add(record);
		
		ResourceRecordSet recordSet = new ResourceRecordSet();
		recordSet.setName("demo.satish.rboomerang.com");
		recordSet.setType(RRType.A);
		recordSet.setTTL(new Long(60));
		recordSet.setResourceRecords(records);
		
		List<Change> changes = new ArrayList<Change>();
		Change change = new Change();
		change.setAction(ChangeAction.UPSERT);
		change.setResourceRecordSet(recordSet);
		changes.add(change);
		
		// Create a batch and add the change to it
		ChangeBatch batch = new ChangeBatch();
		batch.setChanges(changes);
		
		// Create a Request and add the batch to it.
		ChangeResourceRecordSetsRequest request = new ChangeResourceRecordSetsRequest();
		request.setHostedZoneId("ZHM7H6OKNEBJ4");
		request.setChangeBatch(batch);
		
		// send the request
		ChangeResourceRecordSetsResult result = client.changeResourceRecordSets(request);
		
		 ChangeInfo changeInfo = result.getChangeInfo();
		GetChangeRequest getChangeRequest = new GetChangeRequest(changeInfo.getId());
		GetChangeResult resp = client.getChange(getChangeRequest );
		
		while(!"INSYNC".equals(resp.getChangeInfo().getStatus())){
			System.out.println(resp.getChangeInfo().getStatus());
			TimeUnit.SECONDS.sleep(3);
			resp = client.getChange(getChangeRequest );
		}
		System.out.println(resp.toString());
	}
	
	public static void createRecordSetFromHostedZone() {
		AmazonRoute53Client client = new AmazonRoute53Client();
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		List<ResourceRecord> records = new ArrayList<ResourceRecord>();
		ResourceRecord record = new ResourceRecord();
		record.setValue("http://www.satishtest.com");
		records.add(record);
		
		ResourceRecordSet recordSet = new ResourceRecordSet();
		recordSet.setName("demo.satish.rboomerang.com");
		recordSet.setType(RRType.CNAME);
		recordSet.setTTL(new Long(60));
		recordSet.setResourceRecords(records);
		
		// Create the Change
		List<Change> changes = new ArrayList<Change>();
		Change change = new Change();
		change.setAction(ChangeAction.CREATE);
		change.setResourceRecordSet(recordSet);
		changes.add(change);
		
		// Create a batch and add the change to it
		ChangeBatch batch = new ChangeBatch();
		batch.setChanges(changes);
		
		// Create a Request and add the batch to it.
		ChangeResourceRecordSetsRequest request = new ChangeResourceRecordSetsRequest();
		request.setHostedZoneId("ZHM7H6OKNEBJ4");
		request.setChangeBatch(batch);
		
		// send the request
		ChangeResourceRecordSetsResult result = client.changeResourceRecordSets(request);
		System.out.println(result.toString());
		
	}
	
	public static void main(String[] args) throws Exception{
		listRecordSetsForHostedZone();
		createOrUpdateARecord();
	}

}
