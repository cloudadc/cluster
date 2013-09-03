package org.infinispan.quickstart.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class Test {

	public static void main(String[] args) throws IOException {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value-previous");
		Object obj = map.put("key", "value-new");
		System.out.println(obj);

		Cache<String, String> cache = new DefaultCacheManager("infinispan-distribution.xml").getCache();	
		cache.put("key", "value-previous-cache");
		String previous = cache.put("key", "value");
		System.out.println(previous);

		cache.stop();
		
	}

}
