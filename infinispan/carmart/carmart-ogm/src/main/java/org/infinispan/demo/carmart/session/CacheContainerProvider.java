package org.infinispan.demo.carmart.session;

import org.infinispan.manager.EmbeddedCacheManager;

/**
 * 
 * Subclasses should create an instance of a cache manager (DefaultCacheManager, RemoteCacheManager, etc.)
 * 
 * 
 */
public interface CacheContainerProvider {

    public EmbeddedCacheManager getCacheContainer();

}
