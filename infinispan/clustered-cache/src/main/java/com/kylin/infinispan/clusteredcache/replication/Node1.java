package com.kylin.infinispan.clusteredcache.replication;

import org.infinispan.Cache;
import org.infinispan.util.logging.Log;
import org.infinispan.util.logging.LogFactory;

import com.kylin.infinispan.clusteredcache.util.LoggingListener;

public class Node1 extends AbstractNode {

   private Log log = LogFactory.getLog(LoggingListener.class);
   
	public static void main(String[] args) throws Exception {
		new Node1().run();
	}

	public void run() {
		Cache<String, String> cache = getCacheManager().getCache("Demo");

		waitForClusterToForm();

		log.info("About to put key, value into cache on node " + getNodeId());
		
		cache.put("key", "value");
	}
   
	protected int getNodeId() {
		return 1;
	}

}
