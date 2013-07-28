package org.infinispan.loaders;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.UUID;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class FileCacheLoaderTest {

	public static void main(String[] args) throws IOException {
		
		Cache<Object, Object> cache = new DefaultCacheManager("infinispan-loaders-file.xml").getCache("custom-cache-loader");
		
		
		for(int i = 1 ; i <= 15 ; i ++){
			cache.put(i, UUID.randomUUID().toString());
		}
		
		log("Total entities: " + cache.size());
		
		printCacheEntities(cache);
		
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
