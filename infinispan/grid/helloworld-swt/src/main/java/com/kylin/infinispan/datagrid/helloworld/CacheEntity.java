package com.kylin.infinispan.datagrid.helloworld;

public class CacheEntity {

	String key, value, lifespan, maxIdle, alias;

	public CacheEntity(String key, String value, String lifespan,
			String maxIdle, String alias) {
		super();
		this.key = key;
		this.value = value;
		this.lifespan = lifespan;
		this.maxIdle = maxIdle;
		this.alias = alias;
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

	public String getLifespan() {
		return lifespan;
	}

	public void setLifespan(String lifespan) {
		this.lifespan = lifespan;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String toString() {
		return "Cache [key=" + key + ", value=" + value + ", lifespan=" + lifespan + ", maxIdle=" + maxIdle + ", alias=" + alias + "]";
	}
}
