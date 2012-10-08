package com.kylin.infinispan.clusteredcache.distribution;

import org.infinispan.Cache;

import com.kylin.infinispan.clusteredcache.util.LoggingListener;

public class Node1 extends AbstractNode {

   public static void main(String[] args) throws Exception {
      new Node1().run();
   }

	public void run() {
		Cache<String, String> cache = getCacheManager().getCache("Demo");

		cache.addListener(new LoggingListener());

		waitForClusterToForm();
	}
   
	protected int getNodeId() {
		return 1;
	}

}
