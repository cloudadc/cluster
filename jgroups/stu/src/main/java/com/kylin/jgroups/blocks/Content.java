package com.kylin.jgroups.blocks;

import java.io.Serializable;

public class Content implements Serializable {

	private static final long serialVersionUID = -2527511302912722824L;

	private int id;
	
	private String name;
	
	private Object viewId;

	public Content(int id, String name, Object viewId) {
		super();
		this.id = id;
		this.name = name;
		this.viewId = viewId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Content setName(String name) {
		this.name = name;
		return this ;
	}

	public Object getViewId() {
		return viewId;
	}

	public void setViewId(Object viewId) {
		this.viewId = viewId;
	}

	public String toString() {
		return "Content [id=" + id + ", name=" + name + ", viewId=" + viewId + "]";
	}

	
}
