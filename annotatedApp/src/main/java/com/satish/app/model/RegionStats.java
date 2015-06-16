package com.satish.app.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RegionStats {

	private static final String TOTAL_COUNT = "totalCount";
	
	private Map<String,Integer> regionStats = new HashMap<>();

	public RegionStats(){
		this(new HashMap<>());
	}
	
	public RegionStats(Map<String, Integer> stats){
		regionStats.put(TOTAL_COUNT, 0);
		for(Entry<String, Integer> statsEntry : stats.entrySet()){
			addRegionStats(statsEntry.getKey(),statsEntry.getValue());
		}
	}

	public int getRegionStat(String region){
		return regionStats.containsKey(region) ? regionStats.get(region) : 0;
	}

	public void addRegionStats(String region, int stats){
		regionStats.put(region, stats);
		if(!TOTAL_COUNT.equals(region)) {
			regionStats.put(TOTAL_COUNT, regionStats.get(TOTAL_COUNT) + stats);
		}
	}
	
	public void setRegionStats(Map<String,Integer> regionStats){
		this.regionStats = regionStats;
	}
	
	public Map<String,Integer> getRegionStats(){
		return regionStats;
	}


	@Override
	public String toString() {
		return "Region stats: " + String.valueOf(regionStats);
	}



}
