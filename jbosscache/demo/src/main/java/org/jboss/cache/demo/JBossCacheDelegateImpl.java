package org.jboss.cache.demo;

/**
 * Model delegate implementation for JBossCache demo
 */
public class JBossCacheDelegateImpl implements JBossCacheDelegate {
	
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
