package com.tinderbot.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.tinderbot.http.HttpUtil;
import com.tinderbot.http.HttpUtil.ResponseObject;

public class Main  {
    public static void main(String[] args) {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit Google
        driver.get("https://www.facebook.com/dialog/oauth?client_id=464891386855067&redirect_uri=https://www.facebook.com/connect/login_success.html&scope=basic_info,email,public_profile,user_about_me,user_activities,user_birthday,user_education_history,user_friends,user_interests,user_likes,user_location,user_photos,user_relationship_details&response_type=token");

        // Find the text input element by its name
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("pass"));
        WebElement button = driver.findElement(By.name("login"));
        
        email.sendKeys("richardjfalgoust@gmail.com");
        password.sendKeys("ohL4iwahlai");

        // Now submit the form. WebDriver will find the form for us from the element
        button.submit();

        // Check the title of the page
        String url = driver.getCurrentUrl();
        System.out.println("Page url is: " + url);
        
        Matcher matchers = Pattern.compile("https://www.facebook.com/connect/login_success.html#access_token=([\\w]*)&expires_in=([\\d]*)").matcher(url);
        String fb_token = "";
        if(matchers.matches()){
        	fb_token = matchers.group(1);
			System.out.println("Token : "+matchers.group(1));
		}
        Cookie c_user = driver.manage().getCookieNamed("c_user");
        String fb_id = c_user.getValue();
        System.out.println("FB Id : "+fb_id);
        driver.quit();
        
        final String uri = "https://api.gotinder.com/auth";
        
        List<Header> headerList = new ArrayList<>();
		/*headerList.add(new BasicHeader("Content-type", "application/json"));*/
		headerList.add(new BasicHeader("User-Agent", "Tinder/4.0.4 (iPhone; iOS 7.1.1; Scale/2.00)"));
		Header[] headers = headerList.toArray(new Header[headerList.size()]);
		
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("facebook_token", fb_token));
		params.add(new BasicNameValuePair("facebook_id", fb_id));
		
		ResponseObject responseObject = HttpUtil.sendPost(uri, headers, params);
		if(responseObject != null){
			if(responseObject.getStatus() == 200){
				Map<String, Object> map = (Map<String, Object>) responseObject.getObj();
				String token = (String) map.get("token");
				System.out.println("Token : "+token);
				
				headerList = new ArrayList<Header>();
				headerList.add(new BasicHeader("X-Auth-Token", token));
				headerList.add(new BasicHeader("User-Agent", "Tinder/4.0.4 (iPhone; iOS 7.1.1; Scale/2.00)"));
				
				headers = headerList.toArray(new Header[headerList.size()]);
				
				params = new ArrayList<>();
				params.add(new BasicNameValuePair("lat", "48.89364"));
				params.add(new BasicNameValuePair("lon", "2.33739"));
				
				HttpUtil.sendPost("https://api.gotinder.com/user/ping", headers, params);
			}
		}
    }
}