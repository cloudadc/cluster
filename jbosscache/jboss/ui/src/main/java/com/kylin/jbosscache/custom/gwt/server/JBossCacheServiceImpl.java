package com.kylin.jbosscache.custom.gwt.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kylin.jbosscache.custom.gwt.client.JBossCacheService;
import com.kylin.jbosscache.custom.gwt.shared.CacheEntity;
import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;

public class JBossCacheServiceImpl extends RemoteServiceServlet implements JBossCacheService {

	private static final long serialVersionUID = 8519870476773762277L;

	public String ping(String name) throws IllegalArgumentException {
		return "success, " + name;
	}
	
	public List<CacheEntity> getCacheContent(String fqn) throws IllegalArgumentException {

		//TODO -- lookup ejb
		
		List<CacheEntity> result = new ArrayList<CacheEntity>();
		
		if(fqn.equals("/a/b/c")) {
			result.add(new CacheEntity("k1","v1"));
			result.add(new CacheEntity("k2","v2"));
			result.add(new CacheEntity("k3","v3"));
		}
		
		return result;
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

	public Integer addCacheContent(String fqn, String key, String value) throws IllegalArgumentException {

		//TODO -- invoke ejb
		
		System.out.println(fqn + ", " + key + ", " + value);
		
		return 1;
	}

}
