package com.kylin.infinispan.hotrod.api;

import java.util.concurrent.Future;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.util.concurrent.FutureListener;
import org.infinispan.util.concurrent.NotifyingFuture;

import com.kylin.infinispan.common.User;
import com.kylin.infinispan.common.Util;
import com.kylin.infinispan.hotrod.ClientBase;

public class AsyncAPITest extends ClientBase {
	
	private FutureListener futureListener = new FutureListener() {

		public void futureDone(Future future) {
			try {
				Util.println("Async return: " + future.get());
			} catch (Exception e) {
				// Future did not complete successfully
				System.out.println("Help!");
			}
		}
	};

	public static void main(String[] args) {
		new AsyncAPITest().test();
	}

	public void test() {
		RemoteCache<Object, Object> cache = newCache() ;
		cache.put("key1", new User(1, "Kylin Soong", "IT"));
		NotifyingFuture future = cache.getAsync("key");
		future.attachListener(futureListener);
	}
	
	

}
