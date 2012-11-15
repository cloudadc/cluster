package com.kylin.jbosscache.api;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.cache.config.Configuration;

public class CacheAPITest {

	public static void main(String[] args) {

		CacheAPITest test = new CacheAPITest();

		// test.createCacheUseDefault();

		// test.createCacheUseClasspathXML();

		// test.createCacheUseFilepathXML();

		test.cachingRetrieving();
		
		test.cachingRetrievingWithFqn();
		
		test.destroy();

	}

	private void destroy() {

		Cache cache = createCacheUseDefault();
		cache.stop();
		cache.destroy();
	}

	private void cachingRetrievingWithFqn() {

		Cache cache = createCacheUseDefault();
		Node rootNode = cache.getRoot();
		Fqn helloWorldFqn = Fqn.fromString("/root/helloWorld");
		Node helloWorld = rootNode.addChild(helloWorldFqn);
		
		cache.put(helloWorldFqn, "isJBossCache", Boolean.TRUE);
		cache.put(helloWorldFqn, "content", new Content("HelloWorld"));
		
		System.out.println(helloWorld.get("isJBossCache"));
		System.out.println(helloWorld.get("content"));
		System.out.println(cache.getRoot().hasChild(helloWorldFqn));
		
		cache.removeNode(helloWorldFqn);
	}

	private void cachingRetrieving() {

		Cache cache = createCacheUseDefault();
		Node rootNode = cache.getRoot();
		Fqn helloWorldFqn = Fqn.fromString("/root/helloWorld");
		Node helloWorld = rootNode.addChild(helloWorldFqn);
		helloWorld.put("isJBossCache", Boolean.TRUE);
		helloWorld.put("content", new Content("HelloWorld"));

		System.out.println(helloWorld.get("isJBossCache"));
		System.out.println(helloWorld.get("content"));
		System.out.println(helloWorld.getFqn());
		System.out.println(helloWorld.getKeys());

		helloWorld.remove("isJBossCache");
		helloWorld.remove("content");

		System.out.println(helloWorld.get("isJBossCache"));
		System.out.println(helloWorld.get("content"));

		rootNode.removeChild(helloWorldFqn);

	}

	private Cache createCacheUseFilepathXML() {

		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache(
				"/opt/configurations/cache-configuration.xml", false);
		Configuration config = cache.getConfiguration();
		config.setClusterName("Cluster Test");
		cache.create();
		cache.start();

		return cache;
	}

	private Cache createCacheUseClasspathXML() {

		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache("total-replication.xml");

		return cache;
	}

	private Cache createCacheUseDefault() {

		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache();

		return cache;
	}

}
