package com.kylin.jbosscache.custom.model;

import java.io.Serializable;

public class CacheEntity implements Serializable {

	private static final long serialVersionUID = 7059013010361311811L;

	private String key;
	
	private String value;
	
	public CacheEntity() {
		
	}

	public CacheEntity(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return  key + " -> " + value ;
	}
}
