package com.tinderbot.http;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class CommonUtl {
	public static void printMapContent(Map<String, Object> map){
		Set<String> keys = map.keySet();
		Iterator<String> i = keys.iterator();
		while(i.hasNext()){
			String key = i.next();
			System.out.println(key+" => "+map.get(key));
		}
	}
}
