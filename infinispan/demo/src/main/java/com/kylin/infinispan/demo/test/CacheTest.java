package com.kylin.infinispan.demo.test;

import java.io.InputStream;
import java.net.URL;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import com.customized.tools.common.ResourceLoader;

public class CacheTest {
	
	static {
		System.setProperty("java.net.preferIPv4Stack", "true");
	}
	
	private static DefaultCacheManager cacheManager;

	public static void main(String[] args) throws Exception {
		
		URL resource = CacheTest.class.getClassLoader().getResource("infinispan-config.xml");
		InputStream stream = /*resource.openStream();*/ ResourceLoader.getInstance().getResourceAsStream("infinispan-config.xml");
		cacheManager = new DefaultCacheManager(stream);
		cacheManager.getCache().start();
		
		Cache cache = cacheManager.getCache();
		
		long lifespan = cache.getCacheConfiguration().expiration().lifespan();
		long maxIdle = cache.getCacheConfiguration().expiration().maxIdle();
				
		String alias = cache.getCacheManager().getAddress().toString();
		
		System.out.println(alias);
	}

}
