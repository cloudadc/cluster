package com.kylin.jbosscache.custom.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;

public interface JBossCacheServiceAsync {

	void ping(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
    void initTree(AsyncCallback<NodeEntity> callback);
}
