package com.kylin.tankwar.core;

import java.io.Serializable;

import com.kylin.tankwar.Direction;

public class TankView implements Serializable {

	private static final long serialVersionUID = -8129014478250008821L;
	
	private String id;
	
	private boolean good;
	
	private boolean live = true;
	
	private int life = 100;
	
	private int x;
	
	private int y;
	
	private Direction dir;
	
	private Direction ptDir;

	public TankView(String id, boolean good, boolean live, int life, int x, int y, Direction dir, Direction ptDir) {
		super();
		this.id = id;
		this.good = good;
		this.live = live;
		this.life = life;
		this.x = x;
		this.y = y;
		this.dir = dir;
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

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public Direction getPtDir() {
		return ptDir;
	}

	public void setPtDir(Direction ptDir) {
		this.ptDir = ptDir;
	}

	@Override
	public String toString() {
		return "TankDraw [id=" + id + ", good=" + good + ", live=" + live
				+ ", life=" + life + ", x=" + x + ", y=" + y + ", dir=" + dir
				+ ", ptDir=" + ptDir + "]";
	}

	

}
