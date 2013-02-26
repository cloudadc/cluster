package com.kylin.ejb.remote.client.test;

import com.kylin.ejb.remote.client.ClientBase;
import com.kylin.infinispan.custom.InfinispanService;

public class TestClient extends ClientBase{
	
	private InfinispanService service ;

	public static void main(String[] args) throws Exception {
		new TestClient().test();
	}

	public void test() throws Exception {
		service = (InfinispanService) getContext("127.0.0.1").lookup(STR_JNDI);
		service.init();
	}

}
