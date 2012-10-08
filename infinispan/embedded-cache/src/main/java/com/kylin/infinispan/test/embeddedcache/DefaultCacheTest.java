package com.kylin.infinispan.test.embeddedcache;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import static com.kylin.infinispan.test.embeddedcache.util.Assert.assertEqual;
import static com.kylin.infinispan.test.embeddedcache.util.Assert.assertTrue;

public class DefaultCacheTest {

   public static void main(String args[]) throws Exception {
	   
	   Cache<String, User> cache = new DefaultCacheManager().getCache();
	   
	   User user = new User(1, "Kylin Soong", "IT");
	   
	   cache.put("key", user);
	   assertEqual(1, cache.size());
	   assertTrue(cache.containsKey("key"));
	   User value = cache.get("key");
	   assertEqual(user, value);
	   System.out.println(user);
      
	   cache.clear();
	   assertTrue(cache.isEmpty());
	   
	   User newUser = new User(2, "Kobe Bryant", "Player");
	   cache.put("key", user);
	   cache.putIfAbsent("key", newUser);
	   System.out.println(cache.get("key"));
	   cache.clear();
	   
	   cache.put("key", user, 5, TimeUnit.SECONDS);
	   System.out.println(" -> " + cache.get("key"));
	   Thread.sleep(1000 * 10);
	   System.out.println(" -> " + cache.get("key"));
	   
	   System.out.println("DONE");
   }

}
