package com.kylin.infinispan.hotrod;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.util.concurrent.FutureListener;

import com.kylin.infinispan.common.User;
import com.kylin.infinispan.common.Util;

public class AsyncAPITest {
	
	static FutureListener futureListener = new FutureListener() {

		public void futureDone(Future future) {
			try {
				Object obj = future.get();
				Util.println(obj);
			} catch (Exception e) {
				// Future did not complete successfully
				System.out.println("Help!");
			}
		}
	};

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		Set<Future<?>> futures = new HashSet<Future<?>>();
		
		InputStream in = BasicAPITest.class.getClassLoader().getResourceAsStream("hotrod-client.properties");
		Properties props = new Properties();
		props.load(in);
		
		Util.pauseln("Create RemoteCacheManager via " + props.getProperty("infinispan.client.hotrod.server_list"));
		RemoteCacheManager cacheManager = new RemoteCacheManager(props);
		RemoteCache<Object, Object> cache = cacheManager.getCache();
		
		cache.put("key1", new User(1, "Kylin", "IT"));
//		cache.putAsync("key2", new User(2, "Kylin", "IT"));
//		cache.putAsync("key3", new User(3, "Kylin", "IT"));
		
//		Util.println(cache.get("key1"));
		
		cache.getAsync("key1").attachListener(futureListener);
	}

}
