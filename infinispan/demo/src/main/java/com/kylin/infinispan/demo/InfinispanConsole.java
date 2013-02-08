package com.kylin.infinispan.demo;

import java.io.InputStream;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.util.Util;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;
import com.customized.tools.common.ResourceLoader;

public class InfinispanConsole extends TreeInputConsole {
	
	private static final String IMAGE_NAME = "infinispan_icon_32px.gif";
	private static final String TITLE_PREFIX = "Infinispan Demo - " ;
	private static final String CACHE_NAME = "Infinispan-Demo";
	
	private CacheDelegate delegate ;
	
	private InfinispanTableImpl table ;

	public InfinispanConsole(String cacheConfigFile) {
		super("Infinispan");
		
		initCacheDelegate(cacheConfigFile);
		
		String title = TITLE_PREFIX + delegate.getGenericCache().getCacheManager().getAddress();
		table = new InfinispanTableImpl(title, IMAGE_NAME, this, delegate);
	}

	private void initCacheDelegate(String cacheConfigFile) {
		InputStream in = ResourceLoader.getInstance().getResourceAsStream(cacheConfigFile);
		try {
			DefaultCacheManager cacheManager = new DefaultCacheManager(in);
			Cache<String, String> cache = cacheManager.getCache(CACHE_NAME);
			cache.addListener(new CacheListener());
			cache.start();
			delegate = new CacheDelegateImpl(cache);
		} catch (Exception e) {
			throw new IllegalStateException("Initialize CacheDelegate Error", e);
		} finally {
			Util.close(in);
		}
	}

	protected void handleLS(String pointer) {
		for(TreeNode node : getCurrentNode().getSons()){
			println(node.getContent());
		}
	}
	
	protected void handleRM(String pointer) {
		String key = readString("Enter Key:", "key", true);
		if(isRemoving(key)){
			removeTreeNode(key);
		}
	}

	protected void handleADD(String pointer) {

		String key = readString("Enter Key:", "key", true);
		String value = readString("Enter Value:", "value", true);
		long lifespan = readLong("Enter lifespan:", -1);
		long maxIdle = readLong("Enter maxIdle:", -1);
		CachedEntry entry = new CachedEntry(key, value, lifespan, maxIdle, "");
		TreeNode node = new TreeNode(key, entry.toString(), getCurrentNode(), null);
		addTreeNode(node);
	}
	
	protected void handleHELP(String pointer) {
		println("[<ls>] list all nodes");
		println("[<rm>] remove node");
		println("[<add>] add new node");
		println("[<tree>] list whole node architecture");
		println("[<tree> <-l>] list whole node architecture with contents");
		println("[<tree> <-list>] list whole node architecture with contents");
	}

	protected void handlePWD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleCD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleOther(String pointer) {
		handleHELP(pointer);
	}
	
	class CachedEntry {
		String key, value, alias;
		long lifespan = -1, maxIdle = -1;

		public CachedEntry(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public CachedEntry(String key, String value, long lifespan, long maxIdle, String alias) {
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

			CachedEntry that = (CachedEntry) o;

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

}
