package com.tinderbot.servlet;

import com.tinderbot.service.TinderService;
import com.tinderbot.service.impl.TinderServiceImpl;

public class Like {

	public static void main(String[] args) {
		String id = "546a3af87798f45d2d360220";
		String auth_token = "dd4102d6-8046-4c70-916c-d79a4702a22e";
		
		try {
			TinderService tinderService = new TinderServiceImpl();
			tinderService.like(auth_token, id);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

}
