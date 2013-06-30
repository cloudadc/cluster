package com.kylin.jbosscache.custom.gwt.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kylin.jbosscache.custom.gwt.client.JBossCacheService;
import com.kylin.jbosscache.custom.gwt.shared.CacheEntity;
import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;


public class JBossCacheServiceImpl extends RemoteServiceServlet implements JBossCacheService {

	private static final long serialVersionUID = 8519870476773762277L;
	
	@EJB
	private com.kylin.jbosscache.custom.service.JBossCacheService jbosscacheService;

	public String ping(String name) throws IllegalArgumentException {
		return "success, " + name;
	}
	
	public List<CacheEntity> getCacheContent(String fqn) throws IllegalArgumentException {

		try {
			Map map = jbosscacheService.getCacheNodeContent(fqn);
			List<CacheEntity> result = new ArrayList<CacheEntity>();
			Iterator iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				Object key = iterator.next();
				result.add(new CacheEntity(key + "",map.get(key) + ""));
			}
			return result;
		} catch (Exception e) {
			throw new JBossCacheServiceInvokeException("Invoke EJB service error", e);
		}
		
	}

	public NodeEntity initTree(){
		
		try {
			List<String> fqnList = jbosscacheService.getFqnStrs();
			return getNodeEntities(fqnList);
		} catch (Exception e) {
			throw new JBossCacheServiceInvokeException("Invoke EJB service error", e);
		}
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
		if (cur.length() < str.length()) {
			str = cur;
		}

		String result = "";
		for (int i = 0; i < str.length(); i++) {
			if (pre.charAt(i) == cur.charAt(i)) {
				result += pre.charAt(i);
			}
		}

		return result;
	}

	private NodeEntity updateCursor(String pre, String cur, NodeEntity entity) {

		String[] preArray = pre.split("/");
		String[] curArray = cur.split("/");
		int a = preArray.length;
		if (curArray.length < a) {
			a = curArray.length;
		}

		NodeEntity result = null;
		NodeEntity cursor = entity;
		for (int i = 0; i < a; i++) {
			if (preArray[i].compareTo(curArray[i]) == 0) {
				Iterator<NodeEntity> iterator = cursor.getChilds().iterator();
				while (iterator.hasNext()) {
					NodeEntity tmp = iterator.next();
					if (tmp.getName().compareTo(preArray[i]) == 0) {
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
		if (curArray.length < a) {
			a = curArray.length;
		}
		boolean result = false;
		for (int i = 0; i < a; i++) {
			if (preArray[i].compareTo(curArray[i]) == 0) {
				result = true;
				break;
			}
		}
		return result;
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
