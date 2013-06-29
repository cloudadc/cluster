package com.kylin.jbosscache.custom.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kylin.jbosscache.custom.model.CacheEntity;
import com.kylin.jbosscache.custom.model.NodeEntity;

public interface JBossCacheServiceAsync {

	void ping(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void initTree(AsyncCallback<NodeEntity> callback);
    void getCacheContent(String path, AsyncCallback<List<CacheEntity>> callback) throws IllegalArgumentException;
    void addCacheContent(String fqn, String key, String value, AsyncCallback<Integer> callback) throws IllegalArgumentException;
}
