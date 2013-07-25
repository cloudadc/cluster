package org.infinispan.grid.demo;

public class CacheEntry {

	String key, value, alias;
	long lifespan = -1, maxIdle = -1;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getAlias() {
		return alias;
	}

	public long getLifespan() {
		return lifespan;
	}

	public long getMaxIdle() {
		return maxIdle;
	}

	public CacheEntry(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public CacheEntry(String key, String value, long lifespan, long maxIdle, String alias) {
		super();
		this.key = key;
		this.value = value;
		this.lifespan = lifespan;
		this.maxIdle = maxIdle;
		this.alias = alias ;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CacheEntry that = (CacheEntry) o;

		if (lifespan != that.lifespan)
			return false;
		if (maxIdle != that.maxIdle)
			return false;
		if (key != null ? !key.equals(that.key) : that.key != null)
			return false;
		if (value != null ? !value.equals(that.value) : that.value != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (int) (lifespan ^ (lifespan >>> 32));
		result = 31 * result + (int) (maxIdle ^ (maxIdle >>> 32));
		return result;
	}

	public String toString() {
		return "[key=" + key + ", value=" + value + ", lifespan=" + lifespan + ", maxIdle=" + maxIdle + ", alias=" + alias + "]";
	}
}
