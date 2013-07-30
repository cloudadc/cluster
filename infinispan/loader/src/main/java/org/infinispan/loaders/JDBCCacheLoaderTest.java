package org.infinispan.loaders;

import java.io.IOException;
import java.util.UUID;
import java.util.Map.Entry;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class JDBCCacheLoaderTest {

	public static void main(String[] args) throws IOException {

		EmbeddedCacheManager cacheManager = new DefaultCacheManager("infinispan-loaders-mysql.xml");
		
		Cache<Object, Object> cache = cacheManager.getCache("custom-cache-loader");
		
		for(int i = 1 ; i <= 15 ; i ++){
			cache.put(i, UUID.randomUUID().toString());
		}
		
		log("Total entities in cache: " + cache.size());
		
		printCacheEntities(cache);
		
		cache.stop();
		
		cacheManager.stop();
	}
	
	private static void printCacheEntities(Cache<Object, Object> cache) {
		for(Entry<Object, Object> entry : cache.entrySet()){
			log(entry.getKey() + " -> " + entry.getValue());
		}
	}

	public static void log(String s) {
		System.out.println(s);
	}

}
