package com.tinderbot.servlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternCompiler {

	public static void main(String[] args) {
		String url = "https://www.facebook.com/connect/login_success.html#access_token=CAAGm0PX4ZCpsBALbiEJbyGpKrI3sZBFZArlxBBD3nuLHmMCYw1aTRDyhVIEw0WYDh6a1PSpcGI9YXFPiZBXNbSLOvZB5jURf0AnVeWHLJFrDHrFAJXEhar3ympTC4DrgJU17q0uoOpZAm9wkpXBGi2zhdPmRBZA3xMuHqT9vuGCEThnOY7ZCKlrnJyGtc5JXSO2q76GgI7yhEFKZCav9hYgBCpLdczKZC1rZCwZD&expires_in=6939";
		//String url = "Hello my name is Joe";
		Matcher matchers = Pattern.compile("https://www.facebook.com/connect/login_success.html#access_token=([\\w]*)&expires_in=([\\d]*)").matcher(url);
		if(matchers.matches()){
			System.out.println("AW : "+matchers.group(1));
		}
		
	}

}
