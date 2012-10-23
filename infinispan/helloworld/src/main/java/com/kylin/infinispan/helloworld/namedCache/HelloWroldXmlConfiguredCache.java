package com.kylin.infinispan.helloworld.namedCache;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.kylin.infinispan.helloworld.User;

public class HelloWroldXmlConfiguredCache {

	public static void main(String[] args) throws IOException {

		EmbeddedCacheManager manager = new DefaultCacheManager("infinispan.xml");

		Cache<Object, Object> cache = manager.getCache("xml-configured-cache");

		for (int i = 0; i < 10; i++) {
			cache.put("key-" + i, new User(i, "Kylin Soong", "IT"));
		}

		for (int i = 0; i < 10; i++) {
			System.out.println(cache.get("key-" + i));
		}
	}

}
