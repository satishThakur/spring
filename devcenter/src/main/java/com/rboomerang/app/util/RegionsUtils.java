package com.rboomerang.app.util;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

public class RegionsUtils {

	public static List<Region> getAllRegions(){
		List<Region> regions = new ArrayList<Region>();
		for(Regions regionsEnum : Regions.values()){
			if(!Regions.GovCloud.equals(regionsEnum) &&
					!Regions.CN_NORTH_1.equals(regionsEnum)) {
				regions.add(Region.getRegion(regionsEnum));
			}
		}
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
