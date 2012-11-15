package com.kylin.jbosscache.api;

import org.jboss.cache.notifications.annotation.CacheListener;
import org.jboss.cache.notifications.annotation.CacheStarted;
import org.jboss.cache.notifications.annotation.CacheStopped;
import org.jboss.cache.notifications.annotation.NodeCreated;
import org.jboss.cache.notifications.annotation.NodeModified;
import org.jboss.cache.notifications.annotation.NodeMoved;
import org.jboss.cache.notifications.annotation.NodeRemoved;
import org.jboss.cache.notifications.annotation.NodeVisited;
import org.jboss.cache.notifications.event.Event;
import org.jboss.cache.notifications.event.NodeEvent;

@CacheListener
public class MyListener {

	@CacheStarted
	@CacheStopped
	public void cacheStartStopEvent(Event e) {
		switch (e.getType()) {
		case CACHE_STARTED:
			System.out.println("Cache has started");
			break;
		case CACHE_STOPPED:
			System.out.println("Cache has stopped");
			break;
		}
	}

	@NodeCreated
	@NodeRemoved
	@NodeVisited
	@NodeModified
	@NodeMoved
	public void logNodeEvent(NodeEvent e) {
		System.out.println(e.getType() + " on node " + e.getFqn() + " has occured");
	}

}
