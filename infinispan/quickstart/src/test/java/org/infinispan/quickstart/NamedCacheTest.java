package org.infinispan.quickstart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NamedCacheTest {
	
	Cache<Object, Object> cache;

	@Before
	public void setup() {
		EmbeddedCacheManager manager = new DefaultCacheManager();
		manager.defineConfiguration("custom-cache", new ConfigurationBuilder()
				.eviction().strategy(EvictionStrategy.LIRS).maxEntries(10)
				.build());
		cache = manager.getCache("custom-cache");
	}

	@After
	public void cleanup() {
		cache.stop();
		cache = null;
	}

	@Test
	public void namedCacheTest() throws InterruptedException {
		cache.put("key", "value");
		assertEquals(1, cache.size());
		assertTrue(cache.containsKey("key"));
		cache.remove("key");
		assertTrue(cache.isEmpty());
		cache.putIfAbsent("key", "newValue");
		assertTrue(cache.get("key").equals("newValue"));
		cache.put("key", "value", 5, TimeUnit.SECONDS);
		assertTrue(cache.containsKey("key"));
		Thread.sleep(6000);
		assertFalse(cache.containsKey("key"));
	}
}
