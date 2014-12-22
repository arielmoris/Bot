package com.tinderbot.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.tinderbot.http.HttpUtil;
import com.tinderbot.http.HttpUtil.ResponseObject;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/index")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String getUri = "https://www.facebook.com/dialog/oauth?client_id=464891386855067&redirect_uri=https://www.facebook.com/connect/login_success.html&scope=basic_info,email,public_profile,user_about_me,user_activities,user_birthday,user_education_history,user_friends,user_interests,user_likes,user_location,user_photos,user_relationship_details&response_type=token";
		HttpUtil.sendGet(getUri);
		
		final String uri = "https://api.gotinder.com/auth";
		final String fb_token = "CAAGm0PX4ZCpsBAF0BzjASGCYYHKwnLJwZCrF1gJAyX1BNEBbPjeIxoWhXcLBqzutCKZCv1kZBAKhBXugrkBXYK9ZBlARHCZBNqZAgOhw6ZASHZAatAVXlNZA8TMttKJ8czwDRGDDWQL2WxMGod4PLE1ifhkt61d8ZCEtVtLLW4ZAdZBQ7Ei9ODUcku8A0nlRo7ELZCvicaZCmdWUhWpGcC9ywKaZBj6ZBu5mildkexZC4ZD";
		final String fb_id = "100008628790725";
		
		List<Header> headerList = new ArrayList<>();
		/*headerList.add(new BasicHeader("Content-type", "application/json"));*/
		headerList.add(new BasicHeader("User-Agent", "User-Agent: Tinder/3.0.4 (iPhone; iOS 7.1; Scale/2.00)"));
		Header[] headers = headerList.toArray(new Header[headerList.size()]);
		
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("facebook_token", fb_token));
		params.add(new BasicNameValuePair("facebook_id", fb_id));
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
