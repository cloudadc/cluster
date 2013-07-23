package org.infinispan.quickstart;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class Quickstart {

	public static void main(String[] args) {
		
		Cache<Object, Object> c = new DefaultCacheManager().getCache();
		System.out.println(c.getVersion());

	}

}
