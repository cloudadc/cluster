package com.kylin.infinispan.datagrid.helloworld;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.Cache;

/**
 * Stores entries into the cache.
 */
@Named
@RequestScoped
public class PutController {

   @Inject
   private Logger log;

   @Inject
   DefaultCacheManager cacheManager;

   private String key;

   private String value;

   private String message;

   public void putSomething() {
      Cache<String, String> c = cacheManager.getCache();
      c.put(key, value);
      log.info("put: " + key + " -> " + value);
      this.setMessage(key + " = " + value + " added");
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

}
