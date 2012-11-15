package com.kylin.jbosscache.api;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;

public class MyListenerTest {
	
	public void test() {
		
		CacheFactory factory = new DefaultCacheFactory();
		Cache cache = factory.createCache(false);
		MyListener myListener = new MyListener();
		cache.addCacheListener(myListener);
		cache.start();
		
		Node root = cache.getRoot();
		Fqn abcFqn = Fqn.fromString("/a/b/c");
		Node abc = root.addChild(abcFqn);
		abc.put("content", new Content("abc test"));
		abc.get("content");
		cache.removeNode(abcFqn);
		cache.stop();
		cache.destroy();
	}

	public static void main(String[] args) {
		new MyListenerTest().test();
	}

}
