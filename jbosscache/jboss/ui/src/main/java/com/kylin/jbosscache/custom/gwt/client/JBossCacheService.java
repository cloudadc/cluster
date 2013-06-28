package com.kylin.jbosscache.custom.gwt.client;

import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;

public interface JBossCacheService {

	String ping(String name) throws IllegalArgumentException;
	
	NodeEntity initTree();
}
