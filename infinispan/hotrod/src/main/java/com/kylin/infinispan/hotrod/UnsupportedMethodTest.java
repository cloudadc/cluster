package com.kylin.infinispan.hotrod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import com.kylin.infinispan.common.User;
import com.kylin.infinispan.common.Util;

public class UnsupportedMethodTest {

	public static void main(String[] args) throws IOException {

		InputStream in = BasicAPITest.class.getClassLoader().getResourceAsStream("hotrod-client.properties");
		Properties props = new Properties();
		props.load(in);
		
		Util.pauseln("Create RemoteCacheManager via " + props.getProperty("infinispan.client.hotrod.server_list"));
		RemoteCacheManager cacheManager = new RemoteCacheManager(props);
		RemoteCache<Object, Object> cache = cacheManager.getCache();
		Object value = new User(1, "Kylin Soong", "IT");
		cache.put("key", value);
		
		cache.remove("key", value);
	}

}
