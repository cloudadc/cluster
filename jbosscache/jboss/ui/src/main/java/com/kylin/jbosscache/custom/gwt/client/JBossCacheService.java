package com.kylin.jbosscache.custom.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;

@RemoteServiceRelativePath("jbosscache")
public interface JBossCacheService extends RemoteService{

	String ping(String name) throws IllegalArgumentException;
	
	NodeEntity initTree();
}
