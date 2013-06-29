package com.kylin.jbosscache.custom;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
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

import com.kylin.jbosscache.custom.model.CacheEntity;
import com.kylin.jbosscache.custom.model.NodeEntity;
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

	public List<CacheEntity> getCacheContent(String fqn)  throws Exception{
		Map map = getCache().getData(Fqn.fromString(fqn));
		List<CacheEntity> result = new ArrayList<CacheEntity>();
		Iterator iterator = map.keySet().iterator();
		while(iterator.hasNext()) {
			Object key = iterator.next();
			result.add(new CacheEntity(key + "",map.get(key) + ""));
		}
		return result;
	}
	
	List<String> fqnList = new ArrayList<String>();

	public NodeEntity initTree() throws Exception {
		
		initFQNList(getCache().getRoot());
		
		return getNodeEntities(fqnList);
	}
	
	private NodeEntity getNodeEntities(List<String> list) {
		
		List<String> newlist = new ArrayList<String> (list.size());
		for(int i = 0 ; i < list.size() ; i ++) {
			String tmp = list.get(i).substring(1);
			if(tmp.compareTo("") != 0){
				newlist.add(tmp);
			}
		}
		
		Collections.sort(newlist);
		
		NodeEntity entity = new NodeEntity("/");
		
		NodeEntity extCursor = entity;
		for(int i = 0 ; i < newlist.size() ; i ++) {
			
			String str = newlist.get(i);
			if(newlist.get(i).contains("/")){
				if(i > 0 && str.contains(newlist.get(i - 1))) {
					str = str.substring(newlist.get(i - 1).length());
				} else if(i > 0 && !str.contains(newlist.get(i - 1)) && isSamePre(newlist.get(i - 1), str)) {
					extCursor = updateCursor(newlist.get(i - 1), str, entity);
					str = str.substring(getSamePre(newlist.get(i - 1), str).length());
				} else if(i > 0 && !str.contains(newlist.get(i - 1)) && !isSamePre(newlist.get(i - 1), str)) {
					extCursor = entity;
				}
				if(str.startsWith("/")){
					str = str.substring(1);
				}
				String[] array = str.split("/");
				NodeEntity cursor = extCursor;
				for(int j = 0 ; j < array.length ; j ++) {
					NodeEntity tmp = new NodeEntity(array[j]);
					cursor.add(tmp);
					cursor = tmp;
				}
				extCursor = cursor;
			} else {
				NodeEntity tmp = new NodeEntity(str);
				entity.add(tmp);
				extCursor = tmp;
			}

		}
		
		return entity;
	}
	
	private String getSamePre(String pre, String cur) {
		String str = pre;
		if(cur.length() < str.length()) {
			str = cur;
		}
		
		String result = "";
		for(int i = 0 ; i < str.length() ; i ++) {
			if(pre.charAt(i) == cur.charAt(i)){
				result += pre.charAt(i);
			}
		}
		
		return result;
	}

	private NodeEntity updateCursor(String pre, String cur, NodeEntity entity) {
		
		String[] preArray = pre.split("/");
		String[] curArray = cur.split("/");
		int a = preArray.length;
		if(curArray.length < a){
			a = curArray.length;
		}
		
		NodeEntity result = null;
		NodeEntity cursor = entity;
		for(int i = 0 ; i < a ; i ++) {
			if(preArray[i].compareTo(curArray[i]) == 0){
				Iterator<NodeEntity> iterator = cursor.getChilds().iterator();
				while(iterator.hasNext()) {
					NodeEntity tmp = iterator.next();
					if(tmp.getName().compareTo(preArray[i]) == 0){
						cursor = tmp;
						break;
					}
				}
				result = cursor;
			}
		}
		
		return result;
	}

	private boolean isSamePre(String pre, String cur) {
		String[] preArray = pre.split("/");
		String[] curArray = cur.split("/");
		int a = preArray.length;
		if(curArray.length < a){
			a = curArray.length;
		}
		boolean result = false;
		for(int i = 0 ; i < a ; i ++) {
			if(preArray[i].compareTo(curArray[i]) == 0) {
				result = true;
				break;
			}
		}
		return result;
	}

	private void initFQNList(Node root) {
		
//		System.out.println(root.getFqn());
		fqnList.add(root.getFqn().toString());
		
		Iterator<Node> iterator = root.getChildren().iterator();
		while(iterator.hasNext()){
			Node node = iterator.next();
			initFQNList(node);
		}
	}

	public void addCacheContent(String fqn, String key, String value)  throws Exception {
		getCache().put(Fqn.fromString(fqn), key, value);
	}

}
