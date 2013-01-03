package com.kylin.infinispan.stu.configuring;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class ProgrammaticConfiguration {

	public static void main(String[] args) throws IOException {

		EmbeddedCacheManager manager = new DefaultCacheManager();
//		Cache defaultCache = manager.getCache();
		
		Configuration c = new ConfigurationBuilder().clustering().cacheMode(CacheMode.REPL_SYNC).build();
		
		String newCacheName = "repl";
		manager.defineConfiguration(newCacheName, c);
		Cache<String, String> cache = manager.getCache(newCacheName);
	}

}
