package com.tinderbot.servlet;

import com.tinderbot.service.TinderService;
import com.tinderbot.service.impl.TinderServiceImpl;

public class GetToken  {
    public static void main(String[] args) {
        
    	String fb_username = "richardjfalgoust@gmail.com";
    	String fb_password = "ohL4iwahlai";
    	
        TinderService tinderService = new TinderServiceImpl();
        try {
			String[] fbResult 	= tinderService.getFbTokenAndFbId(fb_username, fb_password);
			String fb_token 	= fbResult[0];
			String fb_id 		= fbResult[1];
			
			String auth_token 	= tinderService.getToken(fb_token, fb_id);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
    }
}