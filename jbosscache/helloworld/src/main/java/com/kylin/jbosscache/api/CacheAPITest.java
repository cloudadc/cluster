package com.kylin.jbosscache.api;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.cache.config.Configuration;
import org.jboss.cache.config.Configuration.CacheMode;
import org.jboss.cache.lock.IsolationLevel;
import org.jboss.cache.transaction.GenericTransactionManagerLookup;

public class CacheAPITest {

	public static void main(String[] args) {

		CacheAPITest test = new CacheAPITest();

//		 test.createCacheUseDefault();

//		 test.createCacheUseClasspathXML();

//		 test.createCacheUseFilepathXML();

//		test.cachingRetrieving();
		
//		test.cachingRetrievingWithFqn();
		
//		test.destroy();
		
//		test.configurationCreation();
		
		test.batchTest();

	}

	private void batchTest() {
		
		Configuration config = new Configuration();
		config.setInvocationBatchingEnabled(true);
		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache(config);
		
		cache.put("/a", "a", new Content("a"));
		
		cache.startBatch();
		cache.put("/b", "b", new Content("b"));
		cache.put("/c", "c", new Content("c"));
		cache.put("/d", "d", new Content("d"));
		cache.endBatch(true);
		
		System.out.println(cache.getRoot().getChildren());
	}

	private void configurationCreation() {

		Configuration config = new Configuration();
		config.setTransactionManagerLookupClass(GenericTransactionManagerLookup.class.getName());
		config.setIsolationLevel(IsolationLevel.READ_COMMITTED);
		config.setCacheMode(CacheMode.LOCAL);
		config.setLockAcquisitionTimeout(15000);
		
		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache(config);
	}

	private void destroy() {

		Cache cache = createCacheUseDefault();
		cache.stop();
		cache.destroy();
	}
	
	private void getNode() {
		Cache cache = createCacheUseDefault();
		Node root = cache.getRoot();
		root.addChild(Fqn.fromString("/a/b/c"));
		Node b = root.getChild(Fqn.fromString("/a/b"));
		
		b.put("k1", "v1");
		b.put("k2", "v2");
		b.put("k3", "v3");
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
		Cache cache = factory.createCache("cache-configuration.xml");

		return cache;
	}

	private Cache createCacheUseDefault() {

		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache();

		return cache;
	}

}
