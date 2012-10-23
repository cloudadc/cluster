package com.kylin.infinispan.helloworld.cacheManager;

import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import com.kylin.infinispan.helloworld.User;

public class CachePressTest {

	public static void main(String[] args) throws InterruptedException {

		Properties props = new Properties();
		props.put("infinispan.client.hotrod.server_list", "127.0.0.1:11222");
		RemoteCacheManager cacheManager = new RemoteCacheManager(props);
		RemoteCache<Object, Object> cache = cacheManager.getCache();
		
		for(int i = 0 ; i < 1000000 ; i ++) {
			cache.put("key---" + i, new User(i, "Kylin Soong", "SoftWare Engineer"));
		}
		
		System.out.println(cache.size());
		
		Thread.sleep(Long.MAX_VALUE);
	}

}
