package com.kylin.infinispan.test.embeddedcache;

import static com.kylin.infinispan.test.embeddedcache.util.Assert.assertEqual;
import static com.kylin.infinispan.test.embeddedcache.util.Assert.assertTrue;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

/*
 * infinispan.xml should be in class path
 */
public class XmlConfiguredCacheTest {

   public static void main(String args[]) throws Exception {
	  
		Cache<Object, Object> cache = new DefaultCacheManager("infinispan.xml").getCache("xml-configured-cache");
	   
		assertEqual("xml-configured-cache", cache.getName());
		assertTrue(cache.isEmpty());
		
		for (int i = 0; i < 10; i++) {
			cache.put("key-" + i, new User(i, "Kylin Soong", "IT"));
		}
		
		for (int i = 0; i < 10; i++) {
			User user = (User) cache.get("key-" + i);
			System.out.println(user);
		}
   }

}
