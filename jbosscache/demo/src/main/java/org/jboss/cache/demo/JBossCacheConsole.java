package org.jboss.cache.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import org.jgroups.Address;

import com.customized.tools.cli.TreeInputConsole;
import com.customized.tools.cli.TreeNode;

@CacheListener
public class JBossCacheConsole extends TreeInputConsole {
	
	private static final Logger log = Logger.getLogger(JBossCacheConsole.class);
	
	static final String CACHE_ADD = "add";
	static final String CACHE_MODIFY = "modify";
	static final String CACHE_UPDATE = "update";
	static final String CACHE_REMOVE = "remove";
	static final String CACHE_ADD_NODE = "add node";
	static final String CACHE_REMOVE_NODE = "remove node";
	
	private transient Cache<String, String> cache;
	private List<Address> membership = new LinkedList<Address>();
	private Address coordinator = null;
	
	private boolean isDebugCache, isDebugTreeNode ;
		
	private transient JBossCacheLogger cacheLogger;
	
	private transient Executor executor;
	
	public JBossCacheConsole(String name, TreeNode currentNode, JBossCacheDelegate cacheDelegate, boolean isDebugCache, boolean isDebugTreeNode) {
		
		super(name, currentNode);
		
		this.cache = cacheDelegate.getGenericCache();
		this.isDebugCache = isDebugCache;
		this.isDebugTreeNode = isDebugTreeNode;
		
		init();
	}
	
	protected void updateTreeNodes(String prompt) {
		
		cacheLogger.debugCache("Print Cache Content before execute [" + prompt + "]");
		
		TreeNode rootNode = getRootNode();
		rootNode.getSons().clear();
		
		Node<String, String> root = cache.getRoot();
		rootNode.setContent(root.getData() + "");
		synchTreeNodes(root, rootNode);

		updateCurrentNode(getTreeNode(getAbsolutePath()));
	}

	private void synchTreeNodes(Node<String, String> root, TreeNode rootNode) {
		
		for(Node<String, String> node : root.getChildren()) {
			TreeNode treeNode = new TreeNode(node.getFqn().getLastElementAsString(), node.getData() + "", rootNode, null);
			rootNode.getSons().add(treeNode);
			synchTreeNodes(node, treeNode);
		}
	}

	protected void handleADD(String pointer) {
		
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
		} else {
			super.handleOther(pointer);
		}
	}

	private void handleRemoveNode() {
		
		Fqn<String> fqn = Fqn.fromString(getAbsolutePath());
		Node<String, String> node = cache.getNode(fqn);
		
		String fqnStr = readString("Enter JBossCache Fully Qualified Name:", true);
		
		if(isRemoving(fqnStr + " From JBossCache " + fqn) && node.removeChild(Fqn.fromString(fqnStr))) {
			removeTreeNode(fqnStr);
		}
	}

	private void handleAddNode() {
		
		Node parentNode = cache.getNode(Fqn.fromString(getAbsolutePath()));
		
		String fqn = readString("Enter JBossCache Fully Qualified Name:", true);
		Node node = parentNode.addChild(Fqn.fromString(fqn));
		prompt("Add JBossCache Node, Fully Qualified Name [" + node.getFqn() + "]");
		addTreeNode( new TreeNode(fqn, "", getCurrentNode(), null));
	}
	
	protected void handleHELP(String pointer) {
		super.handleHELP(pointer);
		println("[modify] modify JBossCache Node Data");
		println("[update] update JBossCache Node Data");
		println("[remove] remove JBossCache Node Data");
		println("[add node] add children Node");
		println("[remove node] remove children Node");
	}

	/**
	 * Update/Modify TreeNode Content
	 * 
	 * @param path
	 * @param data
	 */
	private void updateTreeNode(String path, Map<String, String> data) {
		
		log.debug("updateTreeNode path = " + path + ", data = " + data);
		
		TreeNode node = getTreeNode(path);
		node.setContent(data + "");
	}
	
	/**
	 * Add new TreeNode
	 * 
	 * @param path
	 * @param name
	 */
	private void createTreeNode(String path, String name) {

		log.debug("createTreeNode path = " + path );
		
		TreeNode node = getTreeNode(path);
		removeTreeNode(node.getSons(), name);
		node.getSons().add(new TreeNode(name, "", node, null));
	}
	
	/**
	 * Remove TreeNode
	 * 
	 * @param path
	 * @param name
	 */
	private void removeTreeNode(String path, String name) {
		
		log.debug("createTreeNode path = " + path );
		
		TreeNode node = getTreeNode(path);
		removeTreeNode(node.getSons(), name);
	}

	private void init() {
		
		setDebug(isDebugTreeNode);
		
		executor = Executors.newCachedThreadPool();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				cache.stop();
			}
		});

		cache.addCacheListener(this);
		
		cacheLogger = new JBossCacheLogger(cache, isDebugCache);
		cacheLogger.debugCache("Debug Initial Cache Content");
		
		List<Address> mbrship;

		mbrship = getMembers();
		if (mbrship != null && mbrship.size() > 0) {
			membership.clear();
			membership.addAll(mbrship);
			coordinator = mbrship.get(0);
		}
	}
	
	private List<Address> getMembers() {
		try {
			return new ArrayList<Address>(cache.getMembers());
		} catch (Throwable t) {
			log.error("JBossCacheConsole.getMembers(): ", t);
			return null;
		}
	}

	public String readString(String prompt, boolean validation) {
		// add Fqn validation
		return super.readString(prompt, validation);
	}
	
	@CacheStarted
	@CacheStopped
	public void cacheStartStopEvent(final Event e) {
		cacheLogger.log(e);
	}
	
	@NodeCreated
	public void nodeCreated(final NodeEvent e) {
		
		if (e.isPre())
			return;
		
//		String parent = e.getFqn().getParent().toString();
//		String last = e.getFqn().getLastElementAsString();
//		createTreeNode(parent, last);
		
		cacheLogger.log(e);
	}
	
	@NodeRemoved
	@NodeEvicted
	public void nodeRemoved(final NodeEvent e) {
		
		if (e.isPre())
			return;
		
//		String parent = e.getFqn().getParent().toString();
//		String last = e.getFqn().getLastElementAsString();
//		removeTreeNode(parent, last);
		
		cacheLogger.log(e);
	}

	@NodeModified
	public void nodeModified(final NodeModifiedEvent e) {
		
		if (e.isPre())
			return;
		
//		Map<String, String> tmpMap = new HashMap<String, String>();
//		
//		switch(e.getModificationType()) {
//		case PUT_DATA :
//			tmpMap.putAll(e.getCache().getNode(e.getFqn()).getData());
//			tmpMap.putAll(e.getData());
//			break;
//		case REMOVE_DATA :
//			tmpMap.putAll(e.getCache().getNode(e.getFqn()).getData());
//			for(Object obj : e.getData().keySet()) {
//				tmpMap.remove(obj);
//			}
//			break;
//		case PUT_MAP :
//			break;
//		}
//						
//		updateTreeNode(e.getFqn().toString(), tmpMap);
		
		cacheLogger.log(e);
	}
	
	@ViewChanged
	public void viewChange(final ViewChangedEvent e) {
		
		cacheLogger.log(e);
		
		Runnable r = new Runnable() {
			public void run() {
				List<Address> mbrship;
				if (e.getNewView() != null && (mbrship = e.getNewView().getMembers()) != null) {
					membership.clear();
					membership.addAll(mbrship);
					coordinator = mbrship.get(0);
				}
			}
		};
		
		executor.execute(r);
	}

}
