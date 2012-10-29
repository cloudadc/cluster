package com.kylin.jbosscache;

import org.jboss.cache.Cache;

/**
 * Model delegate implementation for JBossCache demo
 */
public class JBossCacheModelDelegate implements CacheModelDelegate {
	
	private Cache<String, String> cache;

	public void setCacheShellVariable(Object cache) {
		this.cache = (Cache<String, String>) cache;
	}

	public Object getCacheShellVariable() {
		return cache;
	}

	public Cache getGenericCache() {
		return cache;
	}
}
