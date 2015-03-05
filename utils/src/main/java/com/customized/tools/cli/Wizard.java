package com.customized.tools.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Wizard  {
	
	private String name;
	
	public Wizard(String name) {
		this.name = name ;
	}

	List<String> orderList = new ArrayList<String>();
	
	List<String> oldOrderList = new ArrayList<String>();
	
	Map<String, String> preContent = new HashMap<String, String>();
	
	Map<String, String> content = new HashMap<String, String>();
	
	public void updateKeyLength() {
		
		oldOrderList.addAll(orderList);
		
		List<String> tmpList = new ArrayList<String>();
		
		int length = maxKeyLength();
		
		for(int i = 0 ; i < orderList.size() ; i ++) {
			String key = orderList.get(i);
			String preValue = content.get(key);
			content.remove(key);
			preContent.remove(key);
			
			String newKey = appendKey(key, length);
			
			tmpList.add(newKey);
			content.put(newKey, preValue);
			preContent.put(newKey, preValue);
		}
		
		orderList.clear();
		orderList.addAll(tmpList);
	}
	
	private void rollBackKey() {
		for(int i = 0 ; i < oldOrderList.size() ; i ++) {
			String key = orderList.get(i);
			String value = content.get(key);
			content.remove(key);
			preContent.remove(key);
			content.put(oldOrderList.get(i), value);
			preContent.put(oldOrderList.get(i), value);
		}
		
		orderList.clear();
		orderList.addAll(oldOrderList);
	}
	
	private String appendKey(String key, int length) {
		
		int times = (length - key.length());
		for(int i = 0 ; i < times ; i ++) {
			key += " ";
		}
		return key;
	}

	private int maxKeyLength() {
		
		int length = 0;
		
		for(String key : orderList) {
			if(key.length() > length) {
				length = key.length();
			}
		}
		
		return length;
	}

	public String getKey(int id) {
		return orderList.get(id);
	}
	
	public void update(int id, String value) {
		content.put(orderList.get(id), value);
	}
	
	public void update(String key, String value) {
		content.put(key, value);
		preContent.put(key, value);
	}
	
	public List<String> getOrderList() {
		return orderList;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public abstract void doInit();
	
	public void doNext() {
		preContent.clear();
		preContent.putAll(content);
	}
	
	public void doPre() {
		content.clear();
		content.putAll(preContent);
	}
	
	public boolean doOk(InputConsole console) {
		if(console.isSettingComplete()) {
			rollBackKey();
			return true ;
		} else {
			return false ;
		}
	}

	public String getPrompt() {
		
		String start = "---------------------- " + name + " Wizard ----------------------------";
		String end = "";
		for(int i = 0 ; i < start.length() ; i ++) {
			end += "-";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(start);
		for(int i = 0 ; i < orderList.size() ; i ++) {
			sb.append("\n      [" + (i + 1) + "]. " + orderList.get(i) + ": " + content.get(orderList.get(i)));
		}
		sb.append("\n                [p]. Previous  [n]. Next  [o]. Ok");
		sb.append("\n    " + end);
		
		return sb.toString();
	}
	
}
