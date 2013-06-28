package com.kylin.jbosscache.custom.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kylin.jbosscache.custom.gwt.client.JBossCacheService;
import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;

public class JBossCacheServiceImpl extends RemoteServiceServlet implements JBossCacheService {

	private static final long serialVersionUID = 8519870476773762277L;

	public String ping(String name) throws IllegalArgumentException {
		return "success, " + name;
	}
	
	public NodeEntity initTree(){
		
		//TODO -- lookup initial JBossCache return 
		
		NodeEntity root = new NodeEntity("/");
		NodeEntity a = new NodeEntity("a");
		NodeEntity d = new NodeEntity("d");
		root.add(a).add(d);
		NodeEntity b = new NodeEntity("b");
		a.add(b);
		NodeEntity c = new NodeEntity("c");
		b.add(c);

		return root;
	}

}
