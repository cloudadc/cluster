package org.infinispan.grid.demo;

import java.util.Set;

import org.infinispan.Cache;

public interface CacheDelegate {
	
	public abstract Cache<String, String> getGenericCache();
	
	public abstract void removeAll(Set<String> keys);
	
	public abstract void remove(String key);
	
	public abstract void destory();
}
