package com.tinderbot.servlet;

import java.util.List;
import java.util.Map;

import com.tinderbot.service.TinderService;
import com.tinderbot.service.impl.TinderServiceImpl;

public class GetRecommendations {
	public static void main(String... args){
		String auth_token = "dd4102d6-8046-4c70-916c-d79a4702a22e";
		TinderService tinderService = new TinderServiceImpl();
		
		List<Map<String, Object>> results = tinderService.getRecommendations(auth_token);
		for(Map<String, Object> result : results){
			String id = (String) result.get("_id");
			tinderService.like(auth_token, id);
		}
	}
}
