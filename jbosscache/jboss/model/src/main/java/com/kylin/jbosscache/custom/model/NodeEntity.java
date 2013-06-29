package com.kylin.jbosscache.custom.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class NodeEntity implements Serializable {

	private static final long serialVersionUID = 3186440586269909446L;

	private String name;
	
	public NodeEntity() {
		
	}

	public NodeEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name ;
	}

	private Set<NodeEntity> childs = new HashSet<NodeEntity>();

	public Set<NodeEntity> getChilds() {
		return childs;
	}
	
	public Set<NodeEntity> add(NodeEntity entity) {
		childs.add(entity);
		return childs;
	}
}
