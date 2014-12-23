package com.tinderbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class HttpUtil {
	
	public static ResponseObject sendPost(String uri, Header[] requestHeaders, List<NameValuePair> params){
		ResponseObject responseObject = null;
		try {
			
			responseObject = new ResponseObject();
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(uri);
			post.setHeaders(requestHeaders);
			post.setEntity(new UrlEncodedFormEntity(params));
			
			HttpResponse response = client.execute(post);
			
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("Status Code: "+statusCode);

			/*Header[] headers = response.getAllHeaders();
			for(Header header : headers){
				System.out.println(header.getName()+" => "+header.getValue());
			}*/
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder sBuffer = new StringBuilder();
			char[] cBuffer = new char[1];
			while(reader.read(cBuffer, 0, 1) != -1){
				sBuffer.append(cBuffer[0]);
			}
			String output = sBuffer.toString();
			System.out.println("Output : "+output);
			Map<String, Object> objectMap = null;
			try {
				Gson gson = new GsonBuilder().serializeNulls().create();
				Type type = new TypeToken<Map<String, Object>>() {}.getType();
				objectMap = gson.fromJson(output, type);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
			
			responseObject.setStatus(response.getStatusLine().getStatusCode());
			responseObject.setMessage(response.getStatusLine().getReasonPhrase());
			responseObject.setObj(objectMap);
			reader.close();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseObject;
	}
	
	public static ResponseObject sendGet(String uri, Header[] requestHeaders, List<NameValuePair> params){
		ResponseObject responseObject = null;
		try {
			
			responseObject = new ResponseObject();
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet(uri);
			get.setHeaders(requestHeaders);
			
			HttpResponse response = client.execute(get);
			/*Header[] headers = response.getAllHeaders();
			for(Header header : headers){
				System.out.println(header.getName()+" => "+header.getValue());
			}*/
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder sBuffer = new StringBuilder();
			char[] cBuffer = new char[1];
			while(reader.read(cBuffer, 0, 1) != -1){
				sBuffer.append(cBuffer[0]);
			}
			String output = sBuffer.toString();
			System.out.println("Output : "+output);
			Map<String, Object> objectMap = null;
			try {
				Gson gson = new GsonBuilder().serializeNulls().create();
				Type type = new TypeToken<Map<String, Object>>() {}.getType();
				objectMap = gson.fromJson(output, type);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
			
			responseObject.setStatus(response.getStatusLine().getStatusCode());
			responseObject.setMessage(response.getStatusLine().getReasonPhrase());
			responseObject.setObj(objectMap);
			reader.close();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseObject;
	}
	
	public static class ResponseObject{
		private int status;
		private String message;
		private Object obj;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Object getObj() {
			return obj;
		}
		public void setObj(Object obj) {
			this.obj = obj;
		}
	}
}
