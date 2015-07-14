package com.rboomerang.app.sandbox;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;


class BucketSize{
	
	private long size = 0;
	
	public void addSize(long size){
		this.size = this.size + size;
	}
	
	public long getSizeInKb(){
		return size/1024;
	}
	
	public long getSizeInMb(){
		return size/(1024 * 1024);
	}
	
	public long getSizeInGb(){
		return getSizeInMb()/1024;
	}
	
	public long getSizeInBytes(){
		return size;
	}
	
}
public class S3BucketSizeClient {
	
	
	public static void main(String[] args){
		Region region = Region.getRegion(Regions.US_WEST_2);
		String sourceBucket = "boomerangsearsperffiles";
		AllFilesPredicate allFilesPredicate = new AllFilesPredicate();
		S3BucketServiceImpl service = new S3BucketServiceImpl(sourceBucket, region);
		
		BucketSize size = new BucketSize();
		service.filter(allFilesPredicate, objectSummary -> {
			size.addSize(objectSummary.getSize());
		});
		
		System.out.println("Size: " + size.getSizeInKb() + " KBytes");
	}

}
