package com.kylin.tankwar.jgroups;

import java.io.Serializable;

import com.kylin.tankwar.Direction;

public class TankDraw implements Serializable {

	private static final long serialVersionUID = -8129014478250008821L;
	
	private String id;
	
	private boolean good;
	
	private boolean live = true;
	
	private int life = 100;
	
	private int x;
	
	private int y;
	
	private Direction ptDir;

	public TankDraw(String id, boolean good, boolean live, int life, int x, int y, Direction ptDir) {
		super();
		this.good = good;
		this.live = live;
		this.life = life;
		this.x = x;
		this.y = y;
		this.ptDir = ptDir;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
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

	public Direction getPtDir() {
		return ptDir;
	}

	public void setPtDir(Direction ptDir) {
		this.ptDir = ptDir;
	}

	public String toString() {
		return "TankDraw [id=" + id + ", good=" + good + ", live=" + live
				+ ", life=" + life + ", x=" + x + ", y=" + y + "]";
	}

	

}
