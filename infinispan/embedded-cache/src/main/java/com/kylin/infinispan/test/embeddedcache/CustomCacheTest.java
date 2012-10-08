package com.kylin.infinispan.test.embeddedcache;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import static com.kylin.infinispan.test.embeddedcache.util.Assert.assertEqual;
import static com.kylin.infinispan.test.embeddedcache.util.Assert.assertTrue;
import static org.infinispan.eviction.EvictionStrategy.LIRS;

public class CustomCacheTest {

   public static void main(String args[]) throws Exception {
      EmbeddedCacheManager manager = new DefaultCacheManager();
      manager.defineConfiguration("custom-cache", new ConfigurationBuilder().eviction().strategy(LIRS).maxEntries(10).build());
      Cache<Object, Object> cache = manager.getCache("custom-cache");
      
      assertEqual("custom-cache", cache.getName());
      assertTrue(cache.isEmpty());
      
		for (int i = 0; i < 10; i++) {
			cache.put("key-" + i, new User(i, "Kylin Soong", "IT"));
		}
		
		for (int i = 0; i < 10; i++) {
			User user = (User) cache.get("key-" + i);
			System.out.println(user);
		}

      System.out.println(cache.size());
   }

}
