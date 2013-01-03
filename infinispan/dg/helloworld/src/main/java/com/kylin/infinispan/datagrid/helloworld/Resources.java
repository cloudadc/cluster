package com.kylin.infinispan.datagrid.helloworld;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import org.infinispan.manager.DefaultCacheManager;

import com.kylin.infinispan.common.User;

/**
 * Provides various resources including a cache manager.
 */
public class Resources {

   @Inject
   MyCacheManagerProvider cacheManagerProvider;

   @Produces
   Logger getLogger(InjectionPoint ip) {
      String category = ip.getMember().getDeclaringClass().getName();
      return Logger.getLogger(category);
   }

   @Produces
   DefaultCacheManager getDefaultCacheManager() {
      return cacheManagerProvider.getCacheManager();
   }

}
