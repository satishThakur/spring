package com.rboomerang.app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.ec2.model.Tag;

public class TagsUtil {

	public static Map<String, String> flattenTags(List<Tag> tags){
		Map<String,String> flattenTags = new HashMap<>();
		if(tags != null){
			for(Tag tag : tags){
				flattenTags.put(tag.getKey(), tag.getValue());
			}
		}
		return flattenTags;
	}

	public static Map<String, String> flattenRdsTags(
			List<com.amazonaws.services.rds.model.Tag> tags) {
		Map<String,String> flattenTags = new HashMap<>();
		if(tags != null){
			for(com.amazonaws.services.rds.model.Tag tag : tags){
				flattenTags.put(tag.getKey(), tag.getValue());
			}
		}
		return flattenTags;
	}

	public static Map<String, String> flattenElbTags(
			List<com.amazonaws.services.elasticloadbalancing.model.Tag> tags) {
		Map<String,String> flattenTags = new HashMap<>();
		if(tags != null){
			for(com.amazonaws.services.elasticloadbalancing.model.Tag tag : tags){
				flattenTags.put(tag.getKey(), tag.getValue());
			}
		}
		return flattenTags;
	}

}
