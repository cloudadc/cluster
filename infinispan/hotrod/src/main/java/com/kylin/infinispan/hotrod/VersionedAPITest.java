package com.kylin.infinispan.hotrod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.VersionedValue;

import com.kylin.infinispan.common.User;
import com.kylin.infinispan.common.Util;

public class VersionedAPITest {

	public static void main(String[] args) throws IOException {
		
		InputStream in = BasicAPITest.class.getClassLoader().getResourceAsStream("hotrod-client.properties");
		Properties props = new Properties();
		props.load(in);
		
		Util.pauseln("Create RemoteCacheManager via " + props.getProperty("infinispan.client.hotrod.server_list"));
		RemoteCacheManager cacheManager = new RemoteCacheManager(props);
		RemoteCache<Object, Object> cache = cacheManager.getCache();
		User value = new User(1, "Kylin Soong", "IT");
		cache.put("key", value);
		VersionedValue versionedValue = cache.getVersioned("key");
		
		Util.pauseln("get 'key' versionedValue, print version and value");
		Util.println("  Version = " + versionedValue.getVersion());
		Util.println("  Value = " + versionedValue.getValue());
		
		Util.println("DONE");
	}

}
