package com.kylin.infinispan.hotrod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import com.kylin.infinispan.common.Util;
import com.kylin.infinispan.hotrod.api.BasicAPITest;

public abstract class ClientBase {
	
	private static RemoteCacheManager cacheManager;

	public RemoteCacheManager newCacheManager()  {
		
		if(null != cacheManager) {
			return cacheManager ;
		}
		
		InputStream in = null;
		
		try {
			in = BasicAPITest.class.getClassLoader().getResourceAsStream("hotrod-client.properties");
			Properties props = new Properties();
			props.load(in);
			
			Util.pauseln("Create RemoteCacheManager via " + props.getProperty("infinispan.client.hotrod.server_list"));
			cacheManager = new RemoteCacheManager(props);
			return cacheManager ;
		} catch (IOException e) {
			throw new RuntimeException("IO Error", e) ;
		} finally {
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException("IO Error", e) ;
				}
			}
		}
	}
	
	/**
	 * Retrieves the default cache from the remote server.
	 * @return
	 */
	public RemoteCache<Object, Object> newCache() {
		return newCacheManager().getCache();
	}
	
	public RemoteCache<Object, Object> newCache(String cacheName) {
		return newCacheManager().getCache(cacheName);
	}
	
	public RemoteCache<Object, Object> newCache(String cacheName, boolean forceReturnValue) {
		return newCacheManager().getCache(cacheName, forceReturnValue);
	}
	
	public RemoteCache<Object, Object> newCache(boolean forceReturnValue) {
		return newCacheManager().getCache(forceReturnValue);
	}
	
	public abstract void test();
}
