package com.kylin.jbosscache.custom.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.kylin.jbosscache.custom.model.CacheEntity;
import com.kylin.jbosscache.custom.model.NodeEntity;

@RemoteServiceRelativePath("jbosscache")
public interface JBossCacheService extends RemoteService{

	String ping(String name) throws IllegalArgumentException;
	
	NodeEntity initTree();
	
	List<CacheEntity> getCacheContent(String path) throws IllegalArgumentException;
	
	Integer addCacheContent(String fqn, String key, String value) throws IllegalArgumentException;
}
