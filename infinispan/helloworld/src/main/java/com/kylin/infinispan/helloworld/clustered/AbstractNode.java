package com.kylin.infinispan.helloworld.clustered;

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

	protected abstract EmbeddedCacheManager createCacheManager()  throws IOException;
		
	protected abstract void run(String node) throws Exception;

}
