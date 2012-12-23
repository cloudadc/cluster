package com.kylin.infinispan.hotrod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import com.kylin.infinispan.common.User;
import com.kylin.infinispan.common.Util;

public class BasicAPITest {

	public static void main(String[] args) throws IOException {
		
		InputStream in = BasicAPITest.class.getClassLoader().getResourceAsStream("hotrod-client.properties");
		Properties props = new Properties();
		props.load(in);
		
		RemoteCacheManager cacheManager = new RemoteCacheManager(props);
		RemoteCache<Object, Object> cache = cacheManager.getCache();
		Object value = new User(1, "Kylin Soong", "IT");
		cache.put("key", value);
		Util.println(cache.get("key"));
		cache.remove("key");
		
		System.out.println("DONE");

	}

}
