package com.kylin.jbosscache.custom.gwt.server;

import java.util.List;

import javax.ejb.EJB;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kylin.jbosscache.custom.gwt.client.JBossCacheService;
import com.kylin.jbosscache.custom.model.CacheEntity;
import com.kylin.jbosscache.custom.model.NodeEntity;

public class JBossCacheServiceImpl extends RemoteServiceServlet implements JBossCacheService {

	private static final long serialVersionUID = 8519870476773762277L;
	
	@EJB
	private com.kylin.jbosscache.custom.JBossCacheService jbosscacheService;

	public String ping(String name) throws IllegalArgumentException {
		return "success, " + name;
	}
	
	public List<CacheEntity> getCacheContent(String fqn) throws IllegalArgumentException {

		try {
			return jbosscacheService.getCacheContent(fqn);
		} catch (Exception e) {
			throw new JBossCacheServiceInvokeException("Invoke EJB service error", e);
		}
		
	}

	public NodeEntity initTree(){
		
		try {
			return jbosscacheService.initTree();
		} catch (Exception e) {
			throw new JBossCacheServiceInvokeException("Invoke EJB service error", e);
		}
	}

	public Integer addCacheContent(String fqn, String key, String value) throws IllegalArgumentException {

		try {
			jbosscacheService.addCacheContent(fqn, key, value);
			return 1;
		} catch (Exception e) {
			throw new JBossCacheServiceInvokeException("Invoke EJB service error", e);
		}
		
		
	}

}
