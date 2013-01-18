package com.kylin.jbosscache.demo;

import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.notifications.event.Event;
import org.jboss.cache.notifications.event.NodeEvent;
import org.jboss.cache.notifications.event.ViewChangedEvent;

public class JBossCacheGUILogger {
	
	private static final Logger log = Logger.getLogger(JBossCacheGUILogger.class);
	
	private Cache cache;
	
	private boolean debugCache;

	public JBossCacheGUILogger(Cache cache, boolean debugCache) {
		this.cache = cache;
		this.debugCache = debugCache ;
	}

	public void log(Event e) {
		
//		System.out.println(e.getType());
		
		switch (e.getType()) {
		case CACHE_STARTED:
			log.debug("Cache has started");
			flush(e.getType());
			break;
		case CACHE_STOPPED:
			log.debug("Cache has stopped");
			flush(e.getType());
			break;
		case NODE_CREATED:
			NodeEvent event = (NodeEvent) e;
			log.debug("created node " + event.getFqn());
			flush(e.getType());
			break;
		case NODE_MODIFIED:
			NodeEvent event1 = (NodeEvent) e;
			log.debug("modified node " + event1.getFqn());
			flush(e.getType());
			break;
		case NODE_REMOVED:
			NodeEvent event2 = (NodeEvent) e;
			log.debug("removed node " + event2.getFqn());
			flush(e.getType());
			break;
		case NODE_EVICTED:
			NodeEvent event3 = (NodeEvent) e;
			log.debug("removed node " + event3.getFqn());
			flush(e.getType());
			break;
		case VIEW_CHANGED:
			ViewChangedEvent event4 = (ViewChangedEvent) e;
			log.debug("view changed " + event4.getNewView().getMembers());
			flush(e.getType());
			break;
		}
	}

	private void flush(Event.Type type) {
		// TODO Auto-generated method stub
		
	}

	
}
