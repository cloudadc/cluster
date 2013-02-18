package com.kylin.jbosscache.custom;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.ha.cachemanager.CacheManager;

@Stateless
@Remote(JBossCacheService.class)
@Local(JBossCacheServiceLocal.class)
public class JBossCacheSession implements JBossCacheServiceLocal, JBossCacheService {
	
	private static final Logger logger = Logger.getLogger(JBossCacheSession.class);
	
	private static final String CACHE_KEY = "my-custom-cache";
	
	private CacheManager cacheManager;
	private Cache cache;

	public void start() throws Exception {
		
		logger.info("CustomService Start");
		
		Context ctx = new InitialContext();
		cacheManager = (CacheManager) ctx.lookup("java:CacheManager");
		cache = cacheManager.getCache(CACHE_KEY, true);
		cache.start();
	}

	public void stop() throws Exception {
		cacheManager.releaseCache(CACHE_KEY);
	}

	public Cache getCache() throws Exception {
		if (null == cache)
			start();
		return cache;
	}

}
