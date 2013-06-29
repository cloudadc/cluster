package com.kylin.jbosscache.custom.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kylin.jbosscache.custom.model.NodeEntity;

public class TestAll {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String> ();
		list.add("/");
		list.add("/a/d");
		list.add("/a");
		list.add("/a/b");
		list.add("/a/b/c");
		list.add("/1/2/3");
		
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
		
		print( 0, entity);
		
		
	}

	private static String getSamePre(String pre, String cur) {
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

	private static NodeEntity updateCursor(String pre, String cur, NodeEntity entity) {
		
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

	private static boolean isSamePre(String pre, String cur) {
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

	private static void print(int index, NodeEntity entity) {
		String pre = "";
		for(int i = 0 ; i < index ; i ++){
			pre = pre + "  ";
		}
		System.out.println(pre + entity);
		index++;
		for(NodeEntity en : entity.getChilds()){
			print(index, en);
		}
	}

}
