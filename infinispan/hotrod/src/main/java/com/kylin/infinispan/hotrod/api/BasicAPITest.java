package com.kylin.infinispan.hotrod.api;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.VersionedValue;

import com.kylin.infinispan.common.User;
import com.kylin.infinispan.common.Util;
import com.kylin.infinispan.hotrod.ClientBase;


public class BasicAPITest extends ClientBase {

	public static void main(String[] args){
		
		new BasicAPITest().test();
		
	}

	public void test() {

		RemoteCache<Object, Object> cache = newCache() ;
		Util.pauseln("Cache Name: " + cache.getName());
		Util.pauseln("Cache Version: " + cache.getVersion());
		
		Object value = new User(1, "Kylin Soong", "IT");
		cache.put("key", value);
		Util.println(cache.get("key"));
		
		VersionedValue versionedValue = cache.getVersioned("key");
		Util.pauseln("get 'key' versionedValue, print version and value");
		Util.println("  Version = " + versionedValue.getVersion());
		Util.println("  Value = " + versionedValue.getValue());
	}

}
