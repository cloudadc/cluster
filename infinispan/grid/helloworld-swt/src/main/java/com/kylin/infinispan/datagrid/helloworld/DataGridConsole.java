package com.kylin.infinispan.datagrid.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.infinispan.container.entries.InternalCacheEntry;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;

/**
 * 
 * mvn clean install dependency:copy-dependencies
 * 
 * java -cp target/dependency/*:target/grid-helloworld.jar -Djava.net.preferIPv4Stack=true com.kylin.infinispan.datagrid.helloworld.DataGridConsole
 * 
 * @author kylin
 *
 */
public class DataGridConsole extends TreeInputConsole {
	
	private CacheDelegate delegate ;

	public DataGridConsole(CacheDelegate delegate) {
		super("Infinispan-DataGrid");	
		this.delegate = delegate;
	}

	protected void handleLS(String pointer) {
		updateTreeNode();
		for(TreeNode node : getCurrentNode().getSons()){
			println(node.getContent());
		}
	}
	
	private void updateTreeNode() {
		
		getCurrentNode().getSons().clear();
			
		String alias = delegate.getGenericCache().getCacheManager().getAddress().toString();
		
		for(String key : delegate.getGenericCache().keySet()) {
			String lifespan = delegate.getGenericCache().getAdvancedCache().getDataContainer().get(key).getLifespan() + "";
			String maxIdle = delegate.getGenericCache().getAdvancedCache().getDataContainer().get(key).getMaxIdle() + "";
			CacheEntry entity = new CacheEntry(key, delegate.getGenericCache().get(key), lifespan, maxIdle,alias);
			TreeNode node = new TreeNode(key, entity.toString(), getCurrentNode(), null);
			addTreeNode(node);
		}
	}

	protected void handleRM(String pointer) {
		String key = readString("Enter Key:", "key", true);
		if(isRemoving(key)){
			delegate.getGenericCache().remove(key);
			removeTreeNode(key);
		}
	}

	protected void handleADD(String pointer) {

		String key = readString("Enter Key:", "key", true);
		String value = readString("Enter Value:", "value", true);
		long lifespan = readLong("Enter lifespan:", -1);
		long maxIdle = readLong("Enter maxIdle:", -1);
		
		CacheEntry entity = new CacheEntry(key, value, lifespan + "", maxIdle + "", delegate.getGenericCache().getCacheManager().getAddress().toString());
		
		prompt("Add " + entity);
		
		// add to DataGrid
		delegate.getGenericCache().put(key, value, lifespan, TimeUnit.MILLISECONDS, maxIdle, TimeUnit.MILLISECONDS);
		
		// add to tree console
		TreeNode node = new TreeNode(key, entity.toString(), getCurrentNode(), null);
		addTreeNode(node);

	}
	
	protected void handleHELP(String pointer) {
		println("[<ls>] list all nodes");
		println("[<rm>] remove node");
		println("[<add>] add new node");
		println("[<tree>] list whole node architecture");
		println("[<tree> <-l>] list whole node architecture with contents");
		println("[<tree> <-list>] list whole node architecture with contents");
		println("[<search>] Search the CacheEntry");
		println("[<exit>] exit the Console, CacheDelegate will destory Cache");
		println("[<quit>] quit the Console");
	}

	protected void handlePWD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleCD(String pointer) {
		handleHELP(pointer);
	}

	protected void handleTREE(String pointer) {
		updateTreeNode();
		super.handleTREE(pointer);
	}

	protected void handleOther(String pointer) {
		if(pointer.equals("exit")) {
			if(isExit()) {
				delegate.destory();
				stop();
			}
		} else if(pointer.equals("quit")) {
			if(isQuit()){
				Runtime.getRuntime().exit(0);
			}
		} else if(pointer.equals("search")) {
			handleSearch(pointer);
		}else {
			handleHELP(pointer);
		}
		
	}
	
	private void handleSearch(String pointer) {

		String key = readString("Enter Key:", "key", true);
		String value = delegate.getGenericCache().get(key);
		InternalCacheEntry cacheEntry = delegate.getGenericCache().getAdvancedCache().getDataContainer().get(key);
		println("Search Result:");
		if(value != null) {
			CacheEntry entity = null;
			if(null != cacheEntry) {
				entity = new CacheEntry(key, value, cacheEntry.getLifespan() + "", cacheEntry.getMaxIdle() + "", "");
			} else {
				entity = new CacheEntry(key, value, "", "", "");
			}
			prompt(entity);
		} else {
			prompt("CacheEntry does not exist");
		}
	}

	public static void main(String[] args) throws IOException {
		
		new DataGridConsole(new CacheDelegateImpl()).start();
	}
	
}
