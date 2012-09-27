package com.kylin.tankwar.core;

import org.apache.log4j.Logger;

import com.kylin.tankwar.Direction;

public class Tank {
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	private static final Logger logger = Logger.getLogger(Tank.class);
	
	private String id;
	
	private boolean isGood;
	
	private boolean isLive ;
	
	private int life ;
	
	private int x;
	
	private int y;
	
	private Direction dir;
	
	private Direction ptDir;

	public Tank(String id, boolean isGood, boolean isLive, int life, int x, int y, Direction dir, Direction ptDir) {
		super();
		this.id = id;
		this.isGood = isGood;
		this.isLive = isLive;
		this.life = life;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.ptDir = ptDir;
	}
	
	public TankView getView() {
		return new TankView(id, isGood, isLive, life, x, y, dir, ptDir);
	}
	
	/**
	 * Update Tank status after either synchronous or asychronous session replication finished
	 * @param tankView
	 */
	public void updateTank(TankView tankView){
		
		this.id = tankView.getId();
		this.isGood = tankView.isGood();
		this.isLive = tankView.isLive();
		this.life = tankView.getLife();
		this.x = tankView.getX();
		this.y = tankView.getY();
		this.dir = tankView.getDir();
		this.ptDir = tankView.getPtDir();
	}

}
