package org.infinispan.grid.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;
import com.customized.tools.common.ResourceLoader;

public class InfinispanConsole extends TreeInputConsole {
	
	private static final String IMAGE_NAME = "infinispan_icon_32px.gif";
	private static final String TITLE_PREFIX = "Infinispan Demo - " ;
	private static final String CACHE_NAME = "Infinispan-Demo";
	
	private CacheDelegate delegate ;
	private String address;
	
	private final InfinispanTableImpl table ;
	
	private ExecutorService asyncExecutor;

	public InfinispanConsole(String cacheConfigFile, boolean isVisible) {
		super("Infinispan");
		
		asyncExecutor = Executors.newFixedThreadPool(1);
		
		initCacheDelegate(cacheConfigFile);
		
		address = delegate.getGenericCache().getCacheManager().getAddress().toString();
		
		String title = TITLE_PREFIX + delegate.getGenericCache().getCacheManager().getAddress();
		table = new InfinispanTableImpl(title, IMAGE_NAME, this, delegate);
		
		if(isVisible) {
			new Thread(new Runnable() {
				public void run() {
					table.start();
				}
			}).start();
		}
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
			try {
				in.close();
			} catch (IOException e) {
				
			}
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
		
		final CacheEntry entry = new CacheEntry(key, value, lifespan, maxIdle, address);
		
		// add to Infinispan
		delegate.getGenericCache().put(key, value, lifespan, TimeUnit.MILLISECONDS, maxIdle, TimeUnit.MILLISECONDS);
		
		// add to tree console
		TreeNode node = new TreeNode(key, entry.toString(), getCurrentNode(), null);
		addTreeNode(node);
		
		// add to SWT table
		asyncExecutor.execute(new Runnable() {

			public void run() {
				table.insertEntry(entry);
			}
		});
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

	private class TableThread implements Runnable {
		
		public TableThread() {
			
		}

		public void run() {
			
		}
		
	}
	
}
