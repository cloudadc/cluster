package org.jgroups.demo.tankwar.model;

import java.io.Serializable;


public class MissileView implements Serializable {

	private static final long serialVersionUID = -4600832875517261198L;
	
	private String id;
	
	private String tankId;

	private int x;
	
	private int y;
	
	private Direction dir;
	
	private boolean isGood;
	
	private boolean isLive = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
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

	public boolean isGood() {
		return isGood;
	}

	public void setGood(boolean isGood) {
		this.isGood = isGood;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public MissileView(String id, String tankId, int x, int y, Direction dir, boolean isGood, boolean isLive) {
		
		super();
		this.id = id;
		this.tankId = tankId;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.isGood = isGood;
		this.isLive = isLive;
	}

	public String toString() {
		return "MissileView [id=" + id + ", tankId=" + tankId + ", x=" + x
				+ ", y=" + y + ", dir=" + dir + ", isGood=" + isGood
				+ ", isLive=" + isLive + "]";
	}

}
