package org.infinispan.grid.demo.test;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

public class LocalCacheTest {

	public static void main(String[] args) throws IOException {
		Cache<Object, Object> c = new DefaultCacheManager("infinispan-local-inter.xml").getCache();
		System.out.println(c.getVersion());
		c.stop();
	}

}
