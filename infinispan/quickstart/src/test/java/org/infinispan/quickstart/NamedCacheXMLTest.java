package org.infinispan.quickstart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NamedCacheXMLTest {
	
	Cache<Object, Object> cache;

	@Before
	public void setup() throws IOException {
		cache = new DefaultCacheManager("namedCache.xml").getCache("custom-cache");
	}

	@After
	public void cleanup() {
		cache.stop();
		cache = null;
	}

	@Test
	public void namedCacheXMLTest() throws InterruptedException {
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
