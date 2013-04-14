package com.kylin.infinispan.datagrid.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
		
		String lifespan = delegate.getGenericCache().getCacheConfiguration().expiration().lifespan() + "";
		String maxIdle = delegate.getGenericCache().getCacheConfiguration().expiration().maxIdle() + "";	
		String alias = delegate.getGenericCache().getCacheManager().getAddress().toString();
		
		for(String key : delegate.getGenericCache().keySet()) {
			CacheEntity entity = new CacheEntity(key, delegate.getGenericCache().get(key), lifespan, maxIdle,alias);
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
		
		CacheEntity entity = new CacheEntity(key, value, lifespan + "", maxIdle + "", delegate.getGenericCache().getCacheManager().getAddress().toString());
		
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
		} else {
			handleHELP(pointer);
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		new DataGridConsole(new CacheDelegateImpl()).start();
	}
	
}
