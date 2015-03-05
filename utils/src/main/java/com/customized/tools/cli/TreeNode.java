package com.customized.tools.cli;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	
	private final String name;
	
	private String content;
	
	private TreeNode father;
	
	private List<TreeNode> sons;

	public TreeNode(String name, String content, TreeNode father, TreeNode son) {
		super();
		this.name = name;
		this.content = content;
		this.father = father;
		sons = new ArrayList<TreeNode>();
		
		addSon(son);
				
		if(null == name) {
			throw new TreeInputConsoleException("TreeNode name can not be null");
		}
		
	}


	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TreeNode getFather() {
		return father;
	}

	public void setFather(TreeNode father) {
		this.father = father;
	}

	public List<TreeNode> getSons() {
		return sons;
	}

	public void setSons(List<TreeNode> sons) {
		this.sons = sons;
	}
	
	public TreeNode addSon(TreeNode son) {
		
		if(null == son) {
			return this;
		}
		
		sons.add(son);
		
		return this;
	}

	public String toString() {
		return getName();
	}

	
}
