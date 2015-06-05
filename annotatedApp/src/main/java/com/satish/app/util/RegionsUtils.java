package com.satish.app.util;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class RegionsUtils {
	
	public static List<Region> getAllRegions(){
		List<Region> regions = new ArrayList<Region>();
		regions.add(Region.getRegion(Regions.US_EAST_1));
		regions.add(Region.getRegion(Regions.US_WEST_1));
		regions.add(Region.getRegion(Regions.US_WEST_2));
		return regions;
		
	}
	
	public static Region getRegionFromName(String name){
		for(Region region : RegionsUtils.getAllRegions()){
			if(region.getName().equals(name)){
				return region;
			}
		}
		return null;
	}

}
