package com.kylin.jbosscache.api;

import java.util.Iterator;
import java.util.Set;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;

public class NodeNameTest {
	
	static Cache cache;
	
	static {
		CacheFactory factory = new DefaultCacheFactory();
		cache = factory.createCache();
		cache.getRoot().addChild(Fqn.fromString("/a/b/c"));
		cache.getRoot().addChild(Fqn.fromString("/a/d"));
	}

	public static void main(String[] args) {
		new NodeNameTest().test(cache.getRoot());
	}

	private void test(Node root) {
		
		System.out.println(root.getFqn());
		
		Iterator<Node> iterator = root.getChildren().iterator();
		while(iterator.hasNext()){
			Node node = iterator.next();
			test(node);
		}
	}

}
