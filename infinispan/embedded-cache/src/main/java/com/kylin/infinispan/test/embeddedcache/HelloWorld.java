package com.kylin.infinispan.test.embeddedcache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class HelloWorld {

   public static void main(String args[]) throws Exception {
	   
      Cache<String, User> cache = new DefaultCacheManager().getCache();
      
      Map<String, User> map = new HashMap<String, User>();
      
      int size = 1000000 ;
      
      Date start = new Date();
      mapTest(map,size );
      System.out.println("Map test Done, spent: " + (new Date().getTime() - start.getTime()));
   
      start = new Date();
      infinispanTest(cache, size);
      System.out.println("Inf test Done, spent: " + (new Date().getTime() - start.getTime()));
   }

	private static void mapTest(Map<String, User> map, int size) {

		for (int i = 0 ; i < size ; i ++) {
			map.put("key-" + i, new User(i, "Kylin Soong", "IT"));
		}
		
		for (int i = 0 ; i < size ; i ++) {
			User user = map.get("key-" + i);
		}
	}

	private static void infinispanTest(Cache<String, User> cache, int size) {
		
		for (int i = 0 ; i < size ; i ++) {
			cache.put("key-" + i, new User(i, "Kylin Soong", "IT"));
		}
		
		for (int i = 0 ; i < size ; i ++) {
			User user = cache.get("key-" + i);
		}
	}

}
