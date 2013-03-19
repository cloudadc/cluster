package com.kylin.tankwar.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.kylin.tankwar.core.MainFrame;


public class Missile {
		
	public static final int XSPEED = 20;
	public static final int YSPEED = 20;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		missileImages = new Image[] {
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif"))
		};
		
		imgs.put("L", missileImages[0]);
		imgs.put("LU", missileImages[1]);
		imgs.put("U", missileImages[2]);
		imgs.put("RU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("D", missileImages[6]);
		imgs.put("LD", missileImages[7]);
	}
	
	private String id;
	
	private String tankId;
	
	private int x, y;
	
	private Direction dir;
	
	private boolean isGood;
	
	private boolean isLive = true;
	
	private MainFrame mainFrame;

	public Missile(String id, String tankId, int x, int y, Direction dir, boolean isGood,
			boolean isLive, MainFrame mainFrame) {
		super();
		this.id = id;
		this.tankId = tankId;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.isGood = isGood;
		this.isLive = isLive;
		this.mainFrame = mainFrame;
	}
	
	public Missile(MissileView view) {
		this.updateMissile(view);
	}
	
	public Missile(MissileView view, MainFrame mainFrame) {
		this.updateMissile(view);
		this.mainFrame = mainFrame;
	}
	
	public String getId() {
		return id;
	}

	public String getTankId() {
		return tankId;
	}

	public void draw(Graphics g, boolean isSelf) {
		
		switch(dir) {
		
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		
		if(isSelf) {
			move();
		}

	}

	private void move() {

		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		
		if(x < 0 || y < 0 || y > MainFrame.GAME_HEIGHT || x > MainFrame.GAME_WIDTH) {
			isLive = false;
		}
		
		mainFrame.getComm().replicateMissile(getMissileView());
		
		if(!isLive) {
			mainFrame.getComm().getMissileMap().remove(id);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public MissileView getMissileView() {
		
		return new MissileView(id, tankId, x, y, dir, isGood, isLive);
	}
	
	public void updateMissile(MissileView view) {
		this.id = view.getId();
		this.tankId = view.getTankId();
		this.x = view.getX();
		this.y = view.getY();
		this.dir = view.getDir();
		this.isGood = view.isGood();
		this.isLive = view.isLive();
	}

	public boolean hitTank(Tank tank) {
		
		if(tank.isGood() != isGood && getRect().intersects(tank.getRect())) {
			tank.setLife(tank.getLife() - 20);
			return true ;
		}
		
		return false ;
	}

	public boolean hitTank(Collection<Tank> values) {
		
		boolean isHit = false ;
		
		for(Tank tank : values) {
			
			if(tank.getId().compareTo(getTankId()) == 0) {
				continue;
			}
			
			if(tank.isGood() != isGood && getRect().intersects(tank.getRect())) {
				isLive = false ;
				isHit = true ;
				break;
			}
		}
		
		return isHit;
	}
	
	public boolean hitWall(Wall wall) {
	
		if(isLive && getRect().intersects(wall.getRect())) {
			isLive = false ;			
			return true ;
		}
		
		return false ;
	}

}
