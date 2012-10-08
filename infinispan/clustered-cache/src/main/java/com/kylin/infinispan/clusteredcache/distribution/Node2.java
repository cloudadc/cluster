package com.kylin.infinispan.clusteredcache.distribution;

import org.infinispan.Cache;

import com.kylin.infinispan.clusteredcache.util.LoggingListener;

public class Node2 extends AbstractNode {

   public static void main(String[] args) throws Exception {
      new Node2().run();
   }

   public void run() {
      Cache<String, String> cache = getCacheManager().getCache("Demo");

      waitForClusterToForm();

      cache.addListener(new LoggingListener());
      
      for (int i = 0; i < 20; i++)
         cache.put("key" + i, "value" + i);
   }
   
   protected int getNodeId() {
      return 2;
   }

}
