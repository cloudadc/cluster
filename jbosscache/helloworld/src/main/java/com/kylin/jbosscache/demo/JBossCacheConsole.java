package com.kylin.jbosscache.demo;

import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.cache.notifications.annotation.CacheListener;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;

@CacheListener
public class JBossCacheConsole extends TreeInputConsole{
	
	static final String CACHE_ADD = "add";
	static final String CACHE_MODIFY = "modify";
	static final String CACHE_UPDATE = "update";
	static final String CACHE_REMOVE = "remove";
	static final String CACHE_ADD_NODE = "add node";
	static final String CACHE_REMOVE_NODE = "remove node";
	
	private Cache<String, String> cache;
	
	private boolean debugCache ;
	
	private JBossCacheLogger cacheLogger;
	
	public JBossCacheConsole(String name, TreeNode currentNode, JBossCacheModelDelegate cacheDelegate, boolean debugCache) {
		
		super(name, currentNode);
		
		this.cache = cacheDelegate.getGenericCache();
		this.debugCache = debugCache;
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				cache.stop();
			}
		});

		cache.addCacheListener(this);
		
		cacheLogger = new JBossCacheLogger(cache, debugCache);
	}
	
	protected void handleADD(String pointer) {
		
		if(null == getCurrentNode()) {
			prompt("no CurrentNode exists");
			return;
		}
		
		String[] array = pointer.split(" ");
		
		if(array.length != 1) {
			promptErrorCommand(pointer);
			return ;
		}
		
		String strFqn = getAbsolutePath();
		prompt("Add/Modify JBossCache via Fully Qualified Name [" + strFqn + "]");
		String key = readString("Enter Key:", true);
		String value = readString("Enter Value:", true);
		
		Fqn<String> fqn = Fqn.fromString(strFqn);
		Node<String, String> node = cache.getNode(fqn);
		node.put(key, value);
		getCurrentNode().setContent(node.getData() + "");
	}

	protected void handleRM(String pointer) {
		
		if(null == getCurrentNode()) {
			prompt("no CurrentNode exists");
			return;
		}
		
		String[] array = pointer.split(" ");
		String key = "";
		if(array.length == 2) {
			key = array[1];
		} else {
			key = readString("Enter Key:", true);
		}
		
		String strFqn = getAbsolutePath();
		Fqn<String> fqn = Fqn.fromString(strFqn);
		Node<String, String> node = cache.getNode(fqn);
		
		if(key.equals("*") && isRemoving("all contents from JBossCache Node " + node.getFqn())) {
			node.clearData();
		} else if(isRemoving(key + " from JBossCache Node " + node.getFqn())) {
			node.remove(key);
		}
		
		getCurrentNode().setContent(node.getData() + "");
	}

	protected void handleOther(String pointer) {
		
		if(pointer.equals(CACHE_MODIFY) || pointer.equals(CACHE_UPDATE)) {
			handleADD(pointer);
		} else if(pointer.equals(CACHE_REMOVE)){
			handleRM(pointer);
		} else if(pointer.equals(CACHE_ADD_NODE)) {
			handleAddNode();
		} else if(pointer.equals(CACHE_REMOVE_NODE)) {
			handleRemoveNode();
		}
		
		super.handleOther(pointer);
	}

	private void handleRemoveNode() {
		
		Fqn<String> fqn = Fqn.fromString(getAbsolutePath());
		Node<String, String> node = cache.getNode(fqn);
		
		String fqnStr = readString("Enter JBossCache Fully Qualified Name:", true);
		
		if(isRemoving(" fqnStr From JBossCache " + fqn) && node.removeChild(Fqn.fromString(fqnStr))) {
			removeTreeNode(fqnStr);
		}
	}

	private void handleAddNode() {
		
		Node parentNode ;
		
		if(null == getCurrentNode()) {
			parentNode = cache.getRoot();
		} else {
			parentNode = cache.getNode(Fqn.fromString(getAbsolutePath()));
		}
		
		String fqn = readString("Enter JBossCache Fully Qualified Name:", true);
		Node node = parentNode.addChild(Fqn.fromString(fqn));
		prompt("Add JBossCache Node, Fully Qualified Name [" + node.getFqn() + "]");
		
		addTreeNode( new TreeNode(fqn, "", getCurrentNode(), null));
	}

	public String readString(String prompt, boolean validation) {
		// add Fqn validation
		return super.readString(prompt, validation);
	}

}
