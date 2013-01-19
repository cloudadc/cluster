package com.kylin.jbosscache.demo;

import org.jboss.cache.Cache;
import org.jboss.cache.notifications.annotation.CacheListener;

@CacheListener
public class JBossCacheConsole {
	
	private Cache cache;
	
	private boolean debugCache ;
	
	private JBossCacheLogger cacheLogger;
	
	public JBossCacheConsole(JBossCacheModelDelegate cacheDelegate, boolean debugCache) {
		
		this.cache = cacheDelegate.getGenericCache();
		this.debugCache = debugCache;
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				cache.stop();
			}
		});

		cache.addCacheListener(this);
		
		cacheLogger = new JBossCacheLogger(cache, debugCache);
	}

}
