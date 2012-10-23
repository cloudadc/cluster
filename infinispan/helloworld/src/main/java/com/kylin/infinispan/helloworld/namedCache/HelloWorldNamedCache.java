package com.kylin.infinispan.helloworld.namedCache;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.kylin.infinispan.helloworld.User;


public class HelloWorldNamedCache {

	public static void main(String[] args) {
		
		EmbeddedCacheManager manager = new DefaultCacheManager();
		
		manager.defineConfiguration("named-cache", new ConfigurationBuilder().eviction().strategy(EvictionStrategy.LIRS).maxEntries(10).build());
		
		Cache<Object, Object> cache = manager.getCache("named-cache");
		
		for (int i = 0; i < 10; i++) {
			cache.put("key-" + i, new User(i, "Kylin Soong", "IT"));
		}
		
		for (int i = 0; i < 10; i++) {
			System.out.println(cache.get("key-" + i));
		}
	}
}
