                   package com.kylin.jbosscache.api;

import org.jboss.cache.Fqn;
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
import org.jboss.cache.notifications.event.NodeModifiedEvent;

@CacheListener
public class MyListener {

//	@CacheStarted
//	@CacheStopped
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

//	@NodeCreated
//	@NodeRemoved
//	@NodeVisited
//	@NodeModified
//	@NodeMoved
//	@CacheStarted
//	@CacheStopped
	public void logNodeEvent(NodeEvent e) {
		Fqn fqn = e.getFqn();
		System.out.println(fqn);
		System.out.println(e.getType() + " on node " + e.getFqn() + " has occured");
	}
	
	@NodeModified
	public void nodeModified(final NodeModifiedEvent e) {
		
		if(e.isPre()) {
			return ;
		} 
		
		switch(e.getModificationType()) {
		case PUT_DATA :
			System.out.println(e.getFqn() + " " + e.getModificationType());
			System.out.println(e.getData());
			System.out.println(e.getCache().getNode(e.getFqn()).getData());
			break;
		case REMOVE_DATA :
			System.out.println(e.getFqn() + " " + e.getModificationType());
			System.out.println(e.getData());
			System.out.println(e.getCache().getNode(e.getFqn()).getData());
			break;
		case PUT_MAP :
			System.out.println(e.getModificationType());
			break;
		}
		
		
		
		System.out.println();
	}

}








