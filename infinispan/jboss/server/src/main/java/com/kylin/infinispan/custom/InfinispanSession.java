package com.kylin.infinispan.custom;


import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;


import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

@Stateless
@Remote(InfinispanService.class)
@Local(InfinispanServiceLocal.class)
public class InfinispanSession implements InfinispanServiceLocal, InfinispanService {
	
	private static final Logger logger = Logger.getLogger(InfinispanSession.class);
	
	private static final String CACHE_KEY = "local-cache-1";
	
	@Resource(lookup = "java:jboss/infinispan/container/custom-cache-container")
	private CacheContainer container;
	
	private Cache<Object, Object> cache;

	public InfinispanSession() {
		
	}

	public void init() throws Exception {
		
		logger.info("CustomService init");
		
		cache = container.getCache();
		showCache(cache);
	}

	public void stop() throws Exception {
	}


	public void showCacheContainer() throws Exception {
		
		init();
		
		showCache(container.getCache("local-cache-1"));
		showCache(container.getCache("local-cache-2"));
		showCache(container.getCache("local-cache-3"));
		
	}

	private void showCache(Cache cache) {
		logger.info("Cache Name: " + cache.getName());
		logger.info("Cache Version: " + cache.getVersion());
		logger.info("Cache Status: " + cache.getStatus());
	}

	public void add(Object key, Object value) throws Exception {
		init();
		cache.put(key, value);
	}

	public void modify(Object key, Object value) throws Exception {
		init();
		cache.put(key, value);
	}

	public Object search(Object key) throws Exception {
		init();
		return cache.get(key);
	}

	public void delete(Object key) throws Exception {
		init();
		cache.remove(key);
	}

	
}
