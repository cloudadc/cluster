package com.customized.tools.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * TreeInputConsole
 * 
 * @author kylin
 *
 */
public class TreeInputConsole extends InputConsole {
	
	private static final String DEFAULT_NAME = "TreeInputConsole";
	
	private String name;

	private TreeNode currentNode;
	
	/**
	 * A TreeInputConsole has one root node, root node no father node
	 */
	private TreeNode rootNode = new TreeNode("/", "", null, null);
	
	private String cursor = "~";
	
	private String cursorStr ;
	
	private BufferedReader in ;
	
	private boolean isDebug = false ;
	
	private Validation  addValidation ;
	
	public TreeInputConsole(String name){
		this(name, null);
	}
	
	public TreeInputConsole(String name, TreeNode currentNode){
		this(name, currentNode, false);
	}

	public TreeInputConsole(String name, TreeNode currentNode, Boolean isDebug) {
		super();
		this.name = name;
		this.currentNode = currentNode;
		this.isDebug = isDebug ;
		
		if(null == name || name.equals("")) {
			this.name = DEFAULT_NAME ;
		}
		
		// register TreeNode to rootNode, if TreeNode didn't have a parent
		if(null != currentNode && null == currentNode.getFather()){
			currentNode.setFather(rootNode);
			rootNode.getSons().add(currentNode);
		}
		
		updateCursorStr(currentNode);
		
		InputStreamReader converter = new InputStreamReader(System.in);
		in = new BufferedReader(converter);
		
		addValidation = new AddNodeValidation();
	}

	public TreeNode getCurrentNode() {
		
		if(null == currentNode) {
			currentNode = getRootNode();
		}
		
		return currentNode;
	}

	public String getName() {
		return name;
	}

	public boolean isDebug() {
		return isDebug;
	}

	/**
	 * Threshold for debug TreeNode information.
	 *   if isDebug is true, all exist TreeNode will be printed
	 *   if isDebug is false, debug logic will be ignored
	 * @param isDebug
	 */
	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	
	/**
	 * add treeNode to current node
	 * @param treeNode
	 */
	public void addTreeNode(TreeNode treeNode) {
		if(treeNode.getFather() == null) {
			treeNode.setFather(getCurrentNode());
		}
		if(!exist(treeNode)) {
			getCurrentNode().addSon(treeNode);
		}
	}
	
	/**
	 * Remove TreeNode from Current TreeNode
	 * @param name
	 */
	public void removeTreeNode(String name) {
		removeTreeNode(getCurrentNode().getSons(), name);
	}

	public void updateCurrentNode(TreeNode currentNode) {
		
		if(null == currentNode) {
			currentNode = getRootNode();
		}
		
		this.currentNode = currentNode;
	}
	
	public void start() throws IOException {
		
		isRunning = true;
		
		String pointer = "";
		
		while (isRunning) {
			
			// for debug TreeNode Content
			printTreeNodes(pointer);
						
			// always print cursor string, simulate Linux Commands
			print(cursorStr);
			
			pointer = in.readLine();
			
			updateTreeNodes(pointer);
			
			switch (type(pointer)) {
			
			case NULL :
				break ;
			case LS :
				handleLS(pointer);
				break;
			case CD :
				handleCD(pointer);
				break;
			case PWD :
				handlePWD(pointer);
				break;
			case RM :
				handleRM(pointer);
				break;
			case ADD :
				handleADD(pointer);
				break;
			case HELP :
				handleHELP(pointer);
				break;
			case TREE :
				handleTREE(pointer);
				break;
			case OTHER :
				handleOther(pointer);
				break;
			}
			
			updateCursorStr(getCurrentNode());		
		}
	}

	/**
	 * Override by subclass for initial TreeNodes 
	 */
	protected void updateTreeNodes(String prompt) {
		
	}

	private boolean isRunning;
	
	public void stop() {
		isRunning = false;
	}

	private PrintWriter out = null ;
	
	private void printTreeNodes(String prompt) {

		if (!isDebug())
			return;
		
		if(null == out) {
			try {
				prompt("Debug TreeNode Content is enable");
				File file = new File(System.currentTimeMillis() + ".log");
				out = new PrintWriter(new FileWriter(file), true);
				prompt("TreeNode Content Stack will output to " + file.getName());
			} catch (IOException e) {
				throw new TreeInputConsoleException("instantiate PrintWriter error", e);
			}
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss SSS]").format(new Date()) + " TreeNode Content Stack(" + prompt + "):");
		sb.append("\n");
		out.print(sb.toString());
		recursivePrint(rootNode, 0, true, out);
		out.flush();
	}
	
	private void recursivePrint(TreeNode root, int index, boolean isPrintDetails, PrintWriter writer) {
		
		StringBuffer sb = new StringBuffer();
		appendPrarentsPrefix(root, sb);
		sb.append(assemble31Holder(index, root));
		if(isPrintDetails) {
			sb.append(root.getName() + "  -  " + root.getContent());
		} else {
			sb.append(root.getName()) ;
		}
			
		if(null != writer) {
			writer.println(sb.toString());
		} else {
			println(sb.toString());
		}
		
		index ++ ;
		
		for(TreeNode son : root.getSons()) {
			recursivePrint(son, index, isPrintDetails, writer);
		}
	}

	String l3holder_1 = "├──", l3holder_2 = "└──", l3holder_3 = "   " ;
	String l1holder_1 = "│", l1holder_2 = " " ;
	
	/**
	 * String prefix = (index -1) * (l1holder + l3holder) + 1 * (l3holder + l1holder)
	 * 
	 * @param root
	 * @param index
	 * @param isPrintDetails
	 */
	private void recursivePrint(TreeNode root, int index, boolean isPrintDetails) {
		recursivePrint(root, index, isPrintDetails, null);
	}
	
	private void appendPrarentsPrefix(TreeNode node, StringBuffer sb) {
		
		if(node.getFather() == null) {
			sb.append("");
		} else {
			String prefix = "";
			TreeNode tnode = node;
			while((tnode = tnode.getFather()) != null){
				String holder = assemble13Holder(tnode) ;
				prefix = holder + prefix;
			}
			sb.append(prefix);
		}
	}
		
	private String assemble13Holder( TreeNode node) {
		
		if(node.getFather() == null) {
			return "";
		}
		
		String prefix = "" ;
		
		if(existBuddy(node)) {
			prefix = l1holder_1 + l3holder_3  ;
		} else {
			prefix = l1holder_2 + l3holder_3  ;
		}
		
		return prefix ;
	}
	
	Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>> ();
	
	private String assemble31Holder(int i, TreeNode node) {
		
		String prefix = "" ;
		
		if(i <= 0 || node.getFather() == null) {
			return "";
		}
		
		String key = node.getFather().getName() + i;
		
		if(existBuddy(node) && !isLast(node, key)) {
			HashSet<String> set = map.get(key);
			if(null == set) {
				set = new HashSet<String>();
			}
			set.add(node.getName());
			map.put(key, set);
			prefix = l3holder_1 + l1holder_2 ;
		} else {
			prefix = l3holder_2 + l1holder_2 ;
		}
		
		return prefix ;
	}


	/**
	 * We assume node have buddies
	 */
	private boolean isLast(TreeNode node, String key) {
		
		HashSet<String> set = map.get(key);
		if(set == null) {
			return false ;
		}
		
		//add all buddies name to Set
		HashSet<String> buddies = new HashSet<String>();
		for(TreeNode tn : node.getFather().getSons()) {
			buddies.add(tn.getName());
		}
		
		//recur all current added Set
		for(String name : set) {
			buddies.remove(name);
		}
		
		if(buddies.size() == 1 && buddies.contains(node.getName())) {
			return true ;
		}
		
		return false;
	}

	protected void handleLS(String pointer) {
		
		String[] array = pointer.split(" ");
		
		boolean isPrintDetail = false;
		
		for (int i = 0 ; i < array.length ; i ++) {
			if(array[i].equals("-l")) {
				isPrintDetail = true ;
			}
		}
		
		StringBuffer sb = new StringBuffer();
		List<TreeNode> nodes = getPrintNodes();
				
		if(isPrintDetail) {
			for(TreeNode node : nodes) {
				sb.append(node.getName() + " " + node.getContent());
				sb.append("\n");
			}
			if (nodes.size() > 0)
				print(sb.toString());
			else 
				println(sb.toString());
		} else {
			for(TreeNode node : nodes) {
				sb.append(node.getName());
				sb.append(TAB);
			}
			println(sb.toString());
		}
	}
	
	protected void handlePWD(String pointer) {
		println(getAbsolutePath());
	}
	
	protected void handleCD(String pointer) {

		String[] array = pointer.split(" ");
		
		if(array.length != 2) {
			promptErrorCommand(pointer);
			return ;
		}
		
		String[] path = array[1].split("/");
		
		for(int i = 0 ; i < path.length ; i ++) {
			if(path[i].equals(".")){
				updateCurrentNode(getCurrentNode());
			} else if(path[i].equals("..")) {
				if(getCurrentNode() != null) {
					updateCurrentNode(getCurrentNode().getFather());
				}
			} else {
				String name = path[i];
				TreeNode node = findNode(getCurrentNode(), name);
				if(null == node) {
					prompt("[" + array[1] + "] does not exist");
					break;
				}
				updateCurrentNode(node);
			}
		}
	}
	
	protected void handleRM(String pointer) {
		
		String[] array = pointer.split(" ");
		
		if(array.length < 2) {
			promptErrorCommand(pointer);
			return ;
		}
		
		if(getCurrentNode().getSons().size() == 0) {
			return ;
		}
		
		if(array.length == 2 && array[1].equals("*")) {
			getCurrentNode().getSons().clear();
			return ;
		}
		
		List<TreeNode> list = new ArrayList<TreeNode>();
		list.addAll(getCurrentNode().getSons());
		
		for(int i = 1 ; i < array.length ; i ++) {
			String name = array[i];
			for(int j = 0 ; j < list.size() ; j ++) {
				if(name.compareTo(list.get(j).getName()) == 0) {
					getCurrentNode().getSons().remove(list.get(j));
				}
			}
		}
	}
	
	protected void handleADD(String pointer) {
		
		String[] array = pointer.split(" ");
		
		if(array.length != 1) {
			promptErrorCommand(pointer);
			return ;
		}
		
		String name = readString("Enter Node Name:", true);
		String content = readString("Enter Node Content:", false);
		TreeNode node = new TreeNode(name, content, getCurrentNode(), null);
		if(addValidation.validate(node)){
			getCurrentNode().getSons().add(node);
		}
	}
	
	protected void handleHELP(String pointer) {
		println("[<ls>] list all nodes");
		println("[<ls> <-l>] list all nodes with contents");
		println("[<ls> <-list>] list all nodes with contents");
		println("[<cd> <PATH>] redirect via PATH");
		println("[<pwd>] show current path");
		println("[<rm> <NODE_NAME> <*>] delete node, * hints delete all son nodes");
		println("[<add>] add new node");
		println("[<tree>] list whole node architecture");
		println("[<tree> <-l>] list whole node architecture with contents");
		println("[<tree> <-list>] list whole node architecture with contents");
	}
	
	/**
	 * Write this code when i was drunk
	 * @param pointer
	 */
	protected void handleTREE(String pointer) {
		
		boolean isPrintDetails = pointer.equals("tree -l") || pointer.equals("tree -list"); 
		map.clear();
		recursivePrint(rootNode, 0, isPrintDetails);
	}
	
	protected void handleOther(String pointer) {
		promptErrorCommand(pointer) ;
	}
	
	protected void promptErrorCommand(String pointer) {
		prompt("[" + pointer + "] can not be recognized");
	}
	
	protected TreeNode getRootNode() {
		return rootNode;
	}
	
	protected String getAbsolutePath() {
		
		String tmp = getCurrentNode().getName();
		
		TreeNode node = getCurrentNode();
		while((node = getFatherNode(node)) != null) {
			String old = tmp;
			if(node.getName().compareTo("/") == 0){
				tmp = node.getName() +  old ;
			} else {
				tmp = node.getName() + File.separator + old ;
			}
		}	
		
		return tmp; 
	}
	
	protected void removeTreeNode(List<TreeNode> nodes, String name) {
		
		int size = nodes.size();
		
		for(int i = 0 ; i < size ; i ++) {
			if(nodes.get(i).getName().compareTo(name) == 0) {
				nodes.remove(i);
				break;
			}
		}
	}
	
	protected TreeNode getTreeNode(TreeNode node, String path) {
		
		path = trimPath(path);
		
		if(path == null || path.equals("")) {
			return node;
		}
		
		String[] array = path.split("/");
		for(int i = 0 ; i < array.length ; i ++) {
			node = findNode(node, array[i]);
		}
		
		if(null == node) {
			throw new TreeInputConsoleException("can not find TreeNode via " + path);
		}
		
		return node;
	}
	
	protected TreeNode getTreeNode(String path) {
		
		path = trimPath(path);
		
		if(path == null || path.equals("")) {
			return getRootNode();
		}
		
		String[] array = path.split("/");
		
		TreeNode node = getRootNode();
		for(int i = 0 ; i < array.length ; i ++) {
			node = findNode(node, array[i]);
		}
		
		if(null == node) {
			throw new TreeInputConsoleException("can not find TreeNode via " + path);
		}
		
		return node;
	}
	
	protected String trimPath(String str) {
		
		String path = str ;

		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		
		if(path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		
		return path;
	}
	
	protected boolean existBuddy(TreeNode node) {
		
		if(node.getFather() == null){
			return false ;
		}
		
		return node.getFather().getSons().size() > 1;
	}
	
	private boolean exist(TreeNode treeNode) {
		for(TreeNode node : getCurrentNode().getSons()){
			if(treeNode.getName().compareTo(node.getName()) == 0) {
				return true ;
			}
		}
		return false;
	}

	private void updateCursorStr(TreeNode node) {
		if(null == node) {
			cursor = "/";
		} else {
			cursor = node.getName() ;
		}
		cursorStr = "[" +name + " " + cursor + "]";
	}

	private List<TreeNode> getPrintNodes() {
		return getCurrentNode().getSons();
	}
	
	private TreeNode findNode(TreeNode node, String name) {
		
		if(null == node) {
			throw new TreeInputConsoleException("findNode Error, node name: " + name);
		}
		
		TreeNode result = null;
		
		for(TreeNode n : node.getSons()) {
			if(n.getName().compareTo(name) == 0) {
				result = n ;
				break;
			}
		}
		
		return result;
	}

	private TreeNode getFatherNode(TreeNode node) {
		return node.getFather();
	}

	private Action type(String pointer) {
		
		if(pointer.toLowerCase().startsWith("cd")) {
			return Action.CD ;
		} else if(pointer.toLowerCase().equals("ls") || pointer.toLowerCase().equals("ls -l") || pointer.toLowerCase().equals("ls -list")) {
			return Action.LS;
		} else if(pointer.toLowerCase().equals("pwd")) {
			return Action.PWD;
		} else if(pointer.toLowerCase().startsWith("rm")) {
			return Action.RM;
		} else if(pointer.toLowerCase().equals("add")) {
			return Action.ADD;
		} else if(pointer.toLowerCase().equals("tree") || pointer.toLowerCase().equals("tree -l")  || pointer.toLowerCase().equals("tree -list")) {
			return Action.TREE;
		}else if(pointer.toLowerCase().equals("help")) {
			return Action.HELP;
		} else if(pointer.equals("")){
			return Action.NULL;
		} else {
			return Action.OTHER;
		}
		
	}
	
	private enum Action {

		NULL,
		LS,
		CD,
		PWD,
		RM,
		ADD,
		HELP,
		TREE,
		OTHER,
	}
	
	protected class AddNodeValidation extends Validation {

		protected boolean validate(Object obj) throws TreeInputConsoleException {
			TreeNode node = (TreeNode) obj;
			if(existBuddy(node)) {
				for(TreeNode tn : node.getFather().getSons()) {
					if(tn.getName().compareTo(node.getName()) == 0) {
						prompt("Illegal TreeNode Name, " + node.getName() + " already exist");
						return false ;
					}
				}
			}
			return true;
		}
		
	}
	
}
