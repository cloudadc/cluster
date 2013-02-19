package com.kylin.ejb.remote.client;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import com.kylin.jbosscache.custom.JBossCacheService;

public class JBossCacheService2NodesClient extends ClientBase{
	
	protected JBossCacheService serviceNode1, serviceNode2 ;
	
	public JBossCacheService2NodesClient(String ipNode1, String ipNode2) throws NamingException {
		serviceNode1 = (JBossCacheService) getContext(ipNode1).lookup(STR_JNDI);
		serviceNode2 = (JBossCacheService) getContext(ipNode2).lookup(STR_JNDI);
	}

	public static void main(String[] args) throws Exception{
		new JBossCacheService2NodesClient("10.66.192.231","10.66.192.48").test();
	}

	public void test() throws Exception {
		serviceNode1.start();
		serviceNode2.start();
		
		Map init = new HashMap();
		init.put("k1", "v1");
		init.put("k2", "Kylin Soong");
		init.put("k3", new byte[1000000]);
		serviceNode1.put("/a/b/c/d", init);
		
		stop(1000 * 10);
		
		System.out.println(serviceNode2.get("/a/b/c"));
	}

}
