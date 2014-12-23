package com.tinderbot.servlet;

import com.tinderbot.service.TinderService;
import com.tinderbot.service.impl.TinderServiceImpl;

public class UpdateLocation {

	public static void main(String[] args) {
		String auth_token = "dd4102d6-8046-4c70-916c-d79a4702a22e";
		String lat = "47.1609152";
		String lon = "2.5018152";
		TinderService tinderService = new TinderServiceImpl();
		
		boolean result = tinderService.updateLocation(auth_token, lat, lon);
	}

}
