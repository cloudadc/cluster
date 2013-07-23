package org.infinispan.quickstart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RemoteCacheTest {
	
	RemoteCache cache;

	@Before
	public void setup() {
		Configuration configuration = new ConfigurationBuilder().addServers("127.0.0.1:11222").build();
		RemoteCacheManager manager = new RemoteCacheManager(configuration);
		cache = manager.getCache();
	}

	@After
	public void cleanup() {
		cache.stop();
		cache = null;
	}

	@Test
	public void remoteCacheTest() throws InterruptedException {
		
		cache.put("key", "value");
		assertEquals(1, cache.size());
		assertTrue(cache.containsKey("key"));
		cache.remove("key");
		assertTrue(cache.isEmpty());
		cache.putIfAbsent("key", "newValue");
		assertTrue(cache.get("key").equals("newValue"));
		cache.put("key", "value", 5, TimeUnit.SECONDS);
		assertTrue(cache.containsKey("key"));
		Thread.sleep(10000);
		assertFalse(cache.containsKey("key"));
	}
}
