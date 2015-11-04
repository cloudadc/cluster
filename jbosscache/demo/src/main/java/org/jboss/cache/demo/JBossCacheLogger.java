package org.jboss.cache.demo;

import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.Node;
import org.jboss.cache.notifications.event.Event;
import org.jboss.cache.notifications.event.NodeEvent;
import org.jboss.cache.notifications.event.ViewChangedEvent;

public class JBossCacheLogger {
	
	private static final Logger log = Logger.getLogger(JBossCacheLogger.class);
	
	private Cache<String, String> cache;
	
	private boolean isDebugCache;

	public JBossCacheLogger(Cache<String, String> cache, boolean isDebugCache) {
		this.cache = cache;
		this.isDebugCache = isDebugCache ;
	}

	public void log(Event e) {
				
		switch (e.getType()) {
		case CACHE_STARTED:
			debugCache("Cache started");
			break;
		case CACHE_STOPPED:
			debugCache("Cache stopped");
			break;
		case NODE_CREATED:
			NodeEvent event = (NodeEvent) e;
			debugCache("Created node " + event.getFqn());
			break;
		case NODE_MODIFIED:
			NodeEvent event1 = (NodeEvent) e;
			debugCache("Modified node " + event1.getFqn());;
			break;
		case NODE_REMOVED:
			NodeEvent event2 = (NodeEvent) e;
			debugCache("Removed node " + event2.getFqn());
			break;
		case NODE_EVICTED:
			NodeEvent event3 = (NodeEvent) e;
			debugCache("Evicted node " + event3.getFqn());
			break;
		case VIEW_CHANGED:
			ViewChangedEvent event4 = (ViewChangedEvent) e;
			debugCache("View changed " + event4.getNewView().getMembers());
			break;
		default:
			break;
		}
	}

	public void debugCache(String prompt) {
		
		if (!isDebugCache)
			return;
		
		Node<String, String> root = cache.getRoot();
		
		StringBuffer sb = new StringBuffer();
		sb.append(prompt + ", JBossCache Node Contents Stack:");
		sb.append("\n");
		
		recursiveCacheNode(root, sb, 0);
		
		log.info(sb.toString());
	}

	private void recursiveCacheNode(Node<String, String> root, StringBuffer sb, int index) {

		String preBlank = countBlank(index ++);
		sb.append(preBlank + root.getFqn() + " - " + root.getData());
		sb.append("\n");
		
		for(Node<String, String> node : root.getChildren()) {
			recursiveCacheNode(node, sb, index);
		}
	}
	
	private String countBlank(int size) {

		String tab = "    ";
		String sum = "";
		for(int i = 0 ; i < size ; i ++) {
			sum += tab ;
		}
		return sum;
	}

	
}
