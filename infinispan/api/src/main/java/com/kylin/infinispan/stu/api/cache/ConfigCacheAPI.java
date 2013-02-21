package com.kylin.infinispan.stu.api.cache;

import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.context.Flag;
import org.infinispan.manager.DefaultCacheManager;

/**
 * Using the ConfigurationBuilder API to Configure the Cache API
 * 
 * @author kylin
 *
 */
public class ConfigCacheAPI {

	public static void main(String[] args) {

		Configuration configuration = new ConfigurationBuilder().clustering().cacheMode(CacheMode.LOCAL).build();
		DefaultCacheManager cacheManager = new DefaultCacheManager();
		String newCacheName = "repl";
		cacheManager.defineConfiguration(newCacheName, configuration);
		Cache<String, String> cache = cacheManager.getCache(newCacheName);
		AdvancedCache advancedCache = cache.getAdvancedCache();
		advancedCache.withFlags(Flag.SKIP_REMOTE_LOOKUP, Flag.SKIP_CACHE_LOAD).put("local", "only");
		advancedCache.addInterceptor(new CustomCommandInterceptor(), 0);
		System.out.println(advancedCache.getName());
	}

}
