package org.infinispan.quickstart;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DefaultCacheTest {
	
	Cache<Object, Object> cache;

	@Before
	public void setup() {
		cache = new DefaultCacheManager().getCache();
	}

	@After
	public void cleanup() {
		cache.stop();
		cache = null;
	}

	@Test
	public void defaultCacheTest() throws InterruptedException {
		
		cache.put("key", "value");

		assertEquals(1, cache.size());
		assertTrue(cache.containsKey("key"));
		
		Object v = cache.remove("key");

		assertEquals("value", v);
		assertTrue(cache.isEmpty());

		cache.putIfAbsent("key", "newValue");
		assertTrue(cache.get("key").equals("newValue"));
		
		cache.put("key", "value", 5, TimeUnit.SECONDS);

		assertTrue(cache.containsKey("key"));

		Thread.sleep(6000);
		assertFalse(cache.containsKey("key"));

	}
}
