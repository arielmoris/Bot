package com.tinderbot.service;

import java.util.List;
import java.util.Map;

public interface TinderService {
	
	/**
	 * Get fb_token and fb_id
	 * @param fb_username
	 * @param fb_password
	 * @return String[0] => fb_token, String[1] => fb_id 
	 */
	public String[] getFbTokenAndFbId(String fb_username, String fb_password);
	
	/**
	 * Get auth_token
	 * @param fb_username
	 * @param fb_password
	 * @return auth_token
	 */
	public String getToken(String fb_token, String fb_userid);
	
	/**
	 * 
	 * @param auth_token
	 * @param lat
	 * @param lon
	 * @return true if updated | false if not
	 */
	public boolean updateLocation(String auth_token, String lat, String lon);
	
	
	/**
	 * 
	 * @param auth_token
	 * @param age_filter_min
	 * @param gender -> 0 - Male | 1 - Female
	 * @param age_filter_max 
	 * @param distance_filter -> in km
	 * @return true if updated | false if not
	 */
	public boolean updateProfile(String auth_token, int age_filter_min, int gender, int age_filter_max, int distance_filter);
	
	/**
	 * 
	 * @param auth_token
	 * @param id_to_report
	 * @param cause_id -> 1 - span | 2 - offensive/inappropriate
	 * @return true | false
	 */
	public boolean reportUser(String auth_token, String id_to_report, int cause_id);
	
	/**
	 * 
	 * @param auth_token
	 * @param receiver_id
	 * @param message
	 * @return true | false
	 */
	public boolean sendMessage(String auth_token, String receiver_id, String message);
	
	/**
	 * 
	 * @param auth_token
	 * @return map of result
	 */
	public Map<String, Object> getUpdates(String auth_token);
	
	/**
	 * 
	 * @param auth_token
	 * @param match_id
	 * @return true if match | false if not
	 */
	public boolean like(String auth_token, String match_id);
	
	/**
	 * 
	 * @param auth_token
	 * @param match_id
	 * @return true | false
	 */
	public boolean pass(String auth_token, String match_id);
	
	/**
	 * 
	 * @param auth_token
	 * @return List of recommendations
	 */
	public List<Map<String, Object>> getRecommendations(String auth_token);
}
