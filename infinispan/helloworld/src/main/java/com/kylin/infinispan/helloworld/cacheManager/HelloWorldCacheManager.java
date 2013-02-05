package com.kylin.infinispan.helloworld.cacheManager;

import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import com.kylin.infinispan.common.User;

public class HelloWorldCacheManager {

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put("infinispan.client.hotrod.server_list", "127.0.0.1:11222");
		RemoteCacheManager cacheManager = new RemoteCacheManager(props);
		RemoteCache<Object, Object> cache = cacheManager.getCache();

		System.out.println(cache);

		cache.put("key", new User(1, "Kylin Soong", "IT"));

		System.out.println(cache.size());

		System.out.println(cache.get("key"));

		cache.remove("key");

		System.out.println(cache.size());

	}

}
