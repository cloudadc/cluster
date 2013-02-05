package com.kylin.infinispan.helloworld.embedded;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import com.kylin.infinispan.common.User;

public class HelloWorldDefaultCache {

	public static void main(String[] args) throws InterruptedException {

		DefaultCacheManager cacheManager = new DefaultCacheManager();

		Cache<Object, Object> cache = cacheManager.getCache();
		Cache<Object, Object> cache01 = cacheManager.getCache("0001");
		Cache<Object, Object> cache02 = cacheManager.getCache("0002");
		Cache<Object, Object> cache03 = cacheManager.getCache("0003");
		
		System.out.println("name = " + cache.getName() + ", version = " + cache.getVersion() + ", status = " + cache.getStatus());
		System.out.println("name = " + cache01.getName() + ", version = " + cache01.getVersion() + ", status = " + cache01.getStatus());
		System.out.println("name = " + cache02.getName() + ", version = " + cache02.getVersion() + ", status = " + cache02.getStatus());
		System.out.println("name = " + cache03.getName() + ", version = " + cache03.getVersion() + ", status = " + cache03.getStatus());
		
		cache.put("key", new User(1, "Kylin Soong", "IT"));
		
		System.out.println(cache.size());
		System.out.println(cache.containsKey("key"));
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
