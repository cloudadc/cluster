package com.kylin.jbosscache.custom.service;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.ha.cachemanager.CacheManager;

import com.kylin.jbosscache.custom.util.PropertiesLoader;

@Stateless
@Remote(JBossCacheService.class)
@Local(JBossCacheServiceLocal.class)
public class JBossCacheSession implements JBossCacheServiceLocal, JBossCacheService {
	
	private static final Logger logger = Logger.getLogger(JBossCacheSession.class);
	
	private static final String CACHE_KEY = PropertiesLoader.getCacheName();
	
	private NumberFormat f;
	
	private CacheManager cacheManager;
	private Cache cache;
	
	public JBossCacheSession() {
		f = NumberFormat.getNumberInstance();
		f.setGroupingUsed(false);
		f.setMaximumFractionDigits(2);
	}

	public Cache getCache() throws Exception {
		if (null == cache){
			lookup();
		}
		return cache;
	}

	public void lookup() throws Exception {
		
		logger.debug("lookup cahce via " + CACHE_KEY);
		
		Context ctx = new InitialContext();
		cacheManager = (CacheManager) ctx.lookup("java:CacheManager");
		cache = cacheManager.getCache(CACHE_KEY, true);
		cache.start();
	}

	public void destory() throws Exception {
		cacheManager.releaseCache(CACHE_KEY);
	}

	public void showCache() throws Exception {

		if (null == cache){
			lookup();
		}
		
		logger.info("Show Cache Info");
		logger.info("Cache Version: " + cache.getVersion());
		logger.info("Cache Status: " + cache.getCacheStatus());
		
	}

	public void put(int size) throws Exception {

		if (null == cache)
			lookup();
		
		byte[] buf = new byte[size];
		cache.put(Fqn.fromString("/a/b/c"), "key", buf);
		logger.info("write " + printBytes(buf.length) + " to JBossCache");
	}

	public void put(String fqn, int size) throws Exception {

		if (null == cache)
			lookup();
		
		byte[] buf = new byte[size];
		cache.put(Fqn.fromString("/a/b/c"), "key", buf);
		logger.info("write " + printBytes(buf.length) + " to JBossCache [" + fqn + "]");
	}
	
	private String printBytes(long bytes) {
        double tmp;

        if(bytes < 1000)
            return bytes + "b";
        if(bytes < 1000000) {
            tmp=bytes / 1000.0;
            return f.format(tmp) + "KB";
        }
        if(bytes < 1000000000) {
            tmp=bytes / 1000000.0;
            return f.format(tmp) + "MB";
        }
        else {
            tmp=bytes / 1000000000.0;
            return f.format(tmp) + "GB";
        }
    }

	public void put(String fqn, Map map) throws Exception {
		
		if (null == cache)
			lookup();
		
		cache.put(Fqn.fromString(fqn), map);
		logger.info("write " + map + " to JBossCache [" + fqn + "]");
	}

	public Map get(String fqn) throws Exception {
		
		if (null == cache)
			lookup();
		
		return cache.getNode(Fqn.fromString(fqn)).getData();
	}

	public void test() {

		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources.properties");
				
		System.out.println("in = " + in);
	}

	
	List<String> fqnList = new ArrayList<String>();

	public List<String> getFqnStrs() throws Exception {
		
		fqnList.clear();
		initFQNList(getCache().getRoot());
		return fqnList;
	}

	private void initFQNList(Node root) {

		// System.out.println(root.getFqn());
		fqnList.add(root.getFqn().toString());

		Iterator<Node> iterator = root.getChildren().iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			initFQNList(node);
		}
	}

	public void addCacheContent(String fqn, String key, String value)  throws Exception {
		logger.info("add " + key + " -> " + value + " to " + fqn);
		getCache().put(Fqn.fromString(fqn), key, value);
	}


	public Map getCacheNodeContent(String fqn) throws Exception {
		logger.info("get Cache Node content [" + fqn + "]");
		return getCache().getData(Fqn.fromString(fqn));
	}

}
