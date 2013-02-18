package com.kylin.jbosscache.custom;

import org.jboss.cache.Cache;

public interface JBossCacheService {
	
	public void start() throws Exception ;
	
	public void stop() throws Exception ;
	
	public Cache getCache() throws Exception ;

}
