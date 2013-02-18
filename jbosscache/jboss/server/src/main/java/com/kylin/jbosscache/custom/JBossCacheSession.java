package com.kylin.jbosscache.custom;

import java.text.NumberFormat;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.ha.cachemanager.CacheManager;

@Stateless
@Remote(JBossCacheService.class)
@Local(JBossCacheServiceLocal.class)
public class JBossCacheSession implements JBossCacheServiceLocal, JBossCacheService {
	
	private static final Logger logger = Logger.getLogger(JBossCacheSession.class);
	
	private static final String CACHE_KEY = "my-custom-cache";
	
	private NumberFormat f;
	
	private CacheManager cacheManager;
	private Cache cache;
	
	public JBossCacheSession() {
		f = NumberFormat.getNumberInstance();
		f.setGroupingUsed(false);
		f.setMaximumFractionDigits(2);
	}

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

	public void showCache() throws Exception {

		if (null == cache)
			start();
		
		logger.info("Show Cache Info");
		logger.info("Cache Version: " + cache.getVersion());
		logger.info("Cache Status: " + cache.getCacheStatus());
		
	}

	public void put(int size) throws Exception {

		if (null == cache)
			start();
		
		byte[] buf = new byte[size];
		cache.put(Fqn.fromString("/a/b/c"), "key", buf);
		logger.info("write " + printBytes(buf.length) + " to JBossCache");
	}

	public void put(String fqn, int size) throws Exception {

		if (null == cache)
			start();
		
		byte[] buf = new byte[size];
		cache.put(Fqn.fromString("/a/b/c"), "key", buf);
		logger.info("write " + printBytes(buf.length) + " to JBossCache [" + fqn + "]");
	}
	
	private String printBytes(long bytes) {
        double tmp;

        if(bytes < 1000)
            return bytes + "b";
        if(bytes < 1000000) {
            tmp=bytes / 1000.0;
            return f.format(tmp) + "KB";
        }
        if(bytes < 1000000000) {
            tmp=bytes / 1000000.0;
            return f.format(tmp) + "MB";
        }
        else {
            tmp=bytes / 1000000000.0;
            return f.format(tmp) + "GB";
        }
    }

}
