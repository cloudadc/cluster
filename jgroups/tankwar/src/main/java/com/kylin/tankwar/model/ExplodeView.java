package com.kylin.tankwar.model;

import java.io.Serializable;

public class ExplodeView implements Serializable{

	private static final long serialVersionUID = 662346405657410579L;

	private int x, y;
	
	private String id;

	public ExplodeView(int x, int y, String id) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return "ExplodeView [x=" + x + ", y=" + y + ", id=" + id + "]";
	}
}
