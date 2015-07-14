package com.rboomerang.app.model;

import java.util.HashMap;
import java.util.Map;

public class ResourcesStatsMapping<T> {
	
	private Map<T, AllResourcesStats> statsMapping = new HashMap<>();

	public Map<T, AllResourcesStats> getStatsMapping() {
		return statsMapping;
	}

	public void setStatsMapping(Map<T, AllResourcesStats> statsMapping) {
		this.statsMapping = statsMapping;
	}
	
	public void addResourcesStats(T key, AllResourcesStats stats){
		statsMapping.put(key, stats);
	}
	
	public AllResourcesStats getResourcesStat(T key){
		return statsMapping.get(key);
	}
	
	@Override
	public String toString() {
		
		return "ResourcesStatsMapping: " + statsMapping.toString();
	}

}
