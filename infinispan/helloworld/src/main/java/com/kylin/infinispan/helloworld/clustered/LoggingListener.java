package com.kylin.infinispan.helloworld.clustered;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.infinispan.util.logging.Log;
import org.infinispan.util.logging.LogFactory;

@Listener
public class LoggingListener {
	
	private Log log = LogFactory.getLog(LoggingListener.class);
	
	@CacheEntryCreated
	public void observeAdd(CacheEntryCreatedEvent<?, ?> event) {
		if (!event.isPre()){			
			System.out.println("\n\nCache Created: [" + event.getKey() + " -> " + event.getCache().get(event.getKey()) + "]");
		}
		
	}

	@CacheEntryRemoved
	public void observeRemove(CacheEntryRemovedEvent<?, ?> event) {
		log.infof("Cache entry with key %s removed in cache %s", event.getKey(), event.getCache());
	}

}
