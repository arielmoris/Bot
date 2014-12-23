package com.tinderbot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.tinderbot.service.TinderService;
import com.tinderbot.util.HttpUtil;
import com.tinderbot.util.HttpUtil.ResponseObject;

public class TinderServiceImpl implements TinderService{
	
	@Override
	public String[] getFbTokenAndFbId(String fb_username, String fb_password){
		String[] result = new String[2];
		
        WebDriver driver = new HtmlUnitDriver();
        driver.get("https://www.facebook.com/dialog/oauth?client_id=464891386855067&redirect_uri=https://www.facebook.com/connect/login_success.html&scope=basic_info,email,public_profile,user_about_me,user_activities,user_birthday,user_education_history,user_friends,user_interests,user_likes,user_location,user_photos,user_relationship_details&response_type=token");

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("pass"));
        WebElement button = driver.findElement(By.name("login"));
        
        email.sendKeys(fb_username);
        password.sendKeys(fb_password);

        button.submit();

        String url = driver.getCurrentUrl();
        System.out.println("Page url is: " + url);
        
        Matcher matchers = Pattern.compile("https://www.facebook.com/connect/login_success.html#access_token=([\\w]*)&expires_in=([\\d]*)").matcher(url);
        String fb_token = "";
        if(matchers.matches()){
        	fb_token = matchers.group(1);
			System.out.println("Token : "+matchers.group(1));
			result[0] = fb_token;
		}else{
			System.out.println("Invalid login");
			throw new RuntimeException();
		}
        
        Cookie c_user = driver.manage().getCookieNamed("c_user");
        String fb_id = c_user.getValue();
        System.out.println("FB Id : "+fb_id);
        result[1] = fb_id;
        driver.quit();
        
		return result;
	}
	
	@Override
	public String getToken(String fb_token, String fb_userid) {	
		String auth_token = "";
		
		final String uri = "https://api.gotinder.com/auth";
		Header[] headers = prepareCommonHeader(auth_token);
		
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("facebook_token", fb_token));
		params.add(new BasicNameValuePair("facebook_id", fb_userid));
		
		ResponseObject responseObject = HttpUtil.sendPost(uri, headers, params);
		if(responseObject != null){
			if(responseObject.getStatus() == 200){
				Map<String, Object> map = (Map<String, Object>) responseObject.getObj();
				auth_token = (String) map.get("token");
				System.out.println("Token : "+auth_token);
			}else{
				throw new RuntimeException("Error : "+responseObject.getStatus()+"=>"+responseObject.getMessage());
			}
		}else{
			throw new RuntimeException("Response object is null");
		}
		return auth_token;
	}

	@Override
	public boolean updateLocation(String auth_token, String lat, String lon) {
		boolean result = false;
		
		Header[] headers = prepareCommonHeader(auth_token);
		List<NameValuePair> params = new ArrayList<>();
		
		params.add(new BasicNameValuePair("lat", lat));
		params.add(new BasicNameValuePair("lon", lon));
		
		ResponseObject responseObject = HttpUtil.sendPost("https://api.gotinder.com/user/ping", headers, params);
		if(responseObject != null){
			if(responseObject.getStatus() == 200){
				Map<String, Object> map = (Map<String, Object>) responseObject.getObj();
				Double status = (Double) map.get("status");
				result = status == 200;
			}else{
				throw new RuntimeException("Error : "+responseObject.getStatus()+"=>"+responseObject.getMessage());
			}
		}else{
			throw new RuntimeException("Response object is null");
		}
		
		return result;
	}

	@Override
	public boolean updateProfile(String auth_token, int age_filter_min,
			int gender, int age_filter_max, int distance_filter) {
		return false;
	}

	@Override
	public boolean reportUser(String auth_token, String id_to_report,
			int cause_id) {
		return false;
	}

	@Override
	public boolean sendMessage(String auth_token, String receiver_id,
			String message) {
		return false;
	}

	@Override
	public Map<String, Object> getUpdates(String auth_token) {
		return null;
	}

	@Override
	public boolean like(String auth_token, String match_id) {
		
		boolean result = false;
		
		Header[] headers = prepareCommonHeader(auth_token);
		List<NameValuePair> params = new ArrayList<>();
		
		ResponseObject responseObject = HttpUtil.sendPost("https://api.gotinder.com/like/"+match_id, headers, params);
		if(responseObject != null){
			if(responseObject.getStatus() == 200){
				Map<String, Object> map = (Map<String, Object>) responseObject.getObj();
				result = Boolean.getBoolean((String)map.get("match_result"));
			}else{
				throw new RuntimeException("Error : "+responseObject.getStatus()+"=>"+responseObject.getMessage());
			}
		}else{
			throw new RuntimeException("Response object is null");
		}
		return result;
	}

	@Override
	public boolean pass(String auth_token, String match_id) {
		return false;
	}

	@Override
	public List<Map<String, Object>> getRecommendations(String auth_token) {
		List<Map<String, Object>> result = new ArrayList<>();
		
		Header[] headers = prepareCommonHeader(auth_token);
		List<NameValuePair> params = new ArrayList<>();
		
		ResponseObject responseObject = HttpUtil.sendPost("https://api.gotinder.com/user/recs", headers, params);
		if(responseObject != null){
			if(responseObject.getStatus() == 200){
				Map<String, Object> map = (Map<String, Object>) responseObject.getObj();
				result = (List<Map<String, Object>>) map.get("results");
				System.out.println(result.size());
			}else{
				throw new RuntimeException("Error : "+responseObject.getStatus()+"=>"+responseObject.getMessage());
			}
		}else{
			throw new RuntimeException("Response object is null");
		}
		
		return result;
	}
	
	private static Header[] prepareCommonHeader(String auth_token){
		
		List<Header> headerList = new ArrayList<>();
		if(!auth_token.equalsIgnoreCase("")){
			headerList.add(new BasicHeader("X-Auth-Token", auth_token));
		}
		headerList.add(new BasicHeader("User-Agent", "Tinder/4.0.4 (iPhone; iOS 7.1.1; Scale/2.00)"));
		Header[] headers = headerList.toArray(new Header[headerList.size()]);
		
		return headers;
	}
	
}
