package org.infinispan.quickstart;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class Quickstart {

	public static void main(String[] args) throws IOException {
		
		Cache<Object, Object> c = new DefaultCacheManager("infinispan-distribution.xml").getCache();
		System.out.println(c.getVersion());

	}

}
