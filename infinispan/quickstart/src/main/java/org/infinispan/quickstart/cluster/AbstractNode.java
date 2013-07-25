package org.infinispan.quickstart.cluster;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

public abstract class AbstractNode {
	
	protected final Cache<String, String> cache ;
	
	public AbstractNode() throws IOException {
		cache = createCacheManager().getCache(getCacheName());
		cache.addListener(new LoggingListener());
	}
	
	protected abstract String getCacheName() ;

	/**
	 * Create CacheManager via configuration file
	 * @return
	 * @throws IOException
	 */
	protected abstract EmbeddedCacheManager createCacheManager()  throws IOException;
	
	/**
	 * Create CacheManager via Infinispan API
	 * @return
	 * @throws IOException
	 */
	protected abstract EmbeddedCacheManager initCacheManager()  throws IOException;
		
	protected abstract void run(String node) throws Exception;

}
