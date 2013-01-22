package com.kylin.jbosscache.demo;

import org.jboss.cache.Cache;

/**
 * Model delegate implementation for JBossCache demo
 */
public class JBossCacheModelDelegateImpl implements JBossCacheModelDelegate {
	
	private Cache<String, String> cache;

	public void setCacheShellVariable(Object cache) {
		this.cache = (Cache<String, String>) cache;
	}

	public Object getCacheShellVariable() {
		return cache;
	}

	public Cache<String, String> getGenericCache() {
		return cache;
	}
}
