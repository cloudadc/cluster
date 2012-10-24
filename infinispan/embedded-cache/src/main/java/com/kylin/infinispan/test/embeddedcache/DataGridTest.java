package com.kylin.infinispan.test.embeddedcache;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class DataGridTest {

	public static void main(String[] args) {

		EmbeddedCacheManager manager = new DefaultCacheManager();
		
		Cache<Object, Object> cache1 = manager.getCache("replSyncCache");
		Cache<Object, Object> cache2 = manager.getCache("replAsyncCache");
		Cache<Object, Object> cache3 = manager.getCache("invalidationSyncCache");
				
	}

}
