package com.kylin.infinispan.helloworld;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class HelloWorld {

	public static void main(String args[]) throws Exception {

		Cache<Object, Object> cache = new DefaultCacheManager().getCache();
		
		System.out.println(cache);
		
		cache.put("key", new User(1, "Kylin Soong", "IT"));
		
		System.out.println(cache.size());
		
		System.out.println(cache.get("key"));
		
		Object obj = cache.remove("key");
		System.out.println(obj);
		
		System.out.println(cache.size());
		
		cache.put("key", new User(1, "Kylin Soong", "Software Engineer"));
		cache.put("key", new User(2, "Kobe Bryant", " Basketball Player"));
		System.out.println(cache.get("key"));
		
		cache.put("key", new User(1, "Kylin Soong", "Software Engineer"), 5, TimeUnit.SECONDS);
		System.out.println(cache.get("key"));
		Thread.sleep(5 * 1000);
		System.out.println(cache.get("key"));
	}

}
