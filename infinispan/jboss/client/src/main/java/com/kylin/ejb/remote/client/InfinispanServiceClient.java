package com.kylin.ejb.remote.client;

import com.kylin.infinispan.custom.InfinispanService;


public class InfinispanServiceClient extends ClientBase{

	
	private InfinispanService service ;
	
	public InfinispanServiceClient() throws Exception {
		
		service = (InfinispanService) getContext().lookup(STR_JNDI);
	}

	/**
	 * This will test `add`, `modify`, `search`, `delete`
	 * 
	 * @throws Exception
	 */
	public void test() throws Exception {	
		service.add("key", "Value - Original");
		service.modify("key", "Value - Modified");
		System.out.println(service.search("key"));
		service.delete("key");
	}
	

	public static void main(String[] args) throws Exception {
		InfinispanServiceClient client = new InfinispanServiceClient();
		client.test();
//		System.out.println(STR_JNDI);
	}
	
	
}
