package com.kylin.jgroups;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kylin.jgroups.performance.LargeState;

public class App {
	
	private static final Logger logger = Logger.getLogger(App.class);
	
	static Set<Class> set = new HashSet<Class>();
	
	static {
		set.add(LargeState.class);
	}

	public static void main(String[] args) {
		logger.info("Registered JGroups Demo,Test Class:");
		
		for(Class c : set) {
			logger.info("  " + c.getName());
		}
	}

}
