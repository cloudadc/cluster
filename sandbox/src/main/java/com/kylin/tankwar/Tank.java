package com.kylin.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.kylin.tankwar.jgroups.TankDraw;



public class Tank implements Serializable{
	
	private static final Logger logger = Logger.getLogger(Tank.class);
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	private boolean live = true;
	private BloodBar bb = new BloodBar();
	
	private int life = 100;
	
	TankFrame tc;
	
	private boolean good;
	
	private int x, y;
	private int oldX, oldY;
	
	private static Random r = new Random();
	
	private boolean bL = false;
	private boolean bU = false;
	private boolean bR = false;
	private boolean bD = false;
		
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	private int step = r.nextInt(12) + 3;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static {
		tankImages = new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif"))
		};
		
		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);
		
	}
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, Direction dir,  TankFrame tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
		
		logger.info("initialize a Tank instance[x= " + x + ", y= " + y + ", good= " + good + ", direction= " + dir );
		
	}
	
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		
		if(good) {
			bb.draw(g);
		}
		
		switch(ptDir) {
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
		
		move();
	}
	
	private void move() {
		
		this.oldX = x;
		this.oldY = y;
		
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
		
		if(this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankFrame.GAME_WIDTH) x = TankFrame.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankFrame.GAME_HEIGHT) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
		
		if(!good) {
			Direction[] dirs = Direction.values();
			if(step == 0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}			
			step --;
			
			//if(r.nextInt(40) > 38) this.fire();
		}		
	}
	
	private void stay() {
		x = oldX;
		y = oldY;
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_F2:
			if (!this.live) {
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		
		locateDirection();
	}
	
	private void locateDirection() {
		
		logger.debug("locate direction [bL= " + bL + ", bU= " + bU + ", bR= " + bR + ", bD= " + bD + "]");
		
		if(bL && !bU && !bR && !bD) {
			dir = Direction.L;
		} else if(bL && bU && !bR && !bD) {
			dir = Direction.LU;
		} else if(!bL && bU && !bR && !bD) {
			dir = Direction.U;
		} else if(!bL && bU && bR && !bD) {
			dir = Direction.RU;
		} else if(!bL && !bU && bR && !bD) {
			dir = Direction.R;
		} else if(!bL && !bU && bR && bD) {
			dir = Direction.RD;
		} else if(!bL && !bU && !bR && bD) {
			dir = Direction.D;
		} else if(bL && !bU && !bR && bD) {
			dir = Direction.LD;
		} else if(!bL && !bU && !bR && !bD) {
			dir = Direction.STOP;
		}
		
		logger.debug("Tank direction: " + dir);
	}

	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_SPACE:
			fire(ptDir);
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		case KeyEvent.VK_F:
			superFire();
			break;
		}
		
		locateDirection();		
	}
	
	
	/**
	 * 
	 * Tank fire will create a Missile, Image cause missile's initial position not exact 
	 * (Tank.WIDTH/2 - Missile.WIDTH/2 , Tank.HEIGHT/2 - Missile.HEIGHT/2)
	 * 
	 */
	public Missile fire(Direction dir) {
		
		if(!live) {
			return null;
		}
		
		int x = 0, y = 0;
		
//		x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2 ;
//		y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2 ;
		
		if(dir == Direction.LU || dir == Direction.RU || dir == Direction.RD || dir == Direction.LD) {
			x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2 + 20;
			y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2 + 20;
		} else if(dir == Direction.U) {
			x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2 + 10;
			y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2 + 10;
		} else if(dir == Direction.R || dir == Direction.L) {
			x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2 + 10;
			y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2 + 13;
		} else if(dir == Direction.D) {
			x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2 + 8;
			y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2 + 10;
		} 
		
		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}
	
	
	public boolean collidesWithWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(java.util.List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	
	private void superFire() {
		Direction[] dirs = Direction.values();
		for(int i=0; i<8; i++) {
			fire(dirs[i]);
		}
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	private class BloodBar implements Serializable{
		
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-10, WIDTH, 10);
			int w = WIDTH * life/100 ;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
	
	public boolean eat(Blood b) {
		if(this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
	private String id = UUID.randomUUID().toString();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public TankDraw getTankDraw() {
		return new TankDraw(id, good, live, life, x, y, ptDir);
	}
	
	public void updateTank(TankDraw td){
		this.id = td.getId();
		this.good = td.isGood();
		this.live = td.isLive();
		this.life = td.getLife();
		this.x = td.getX();
		this.y = td.getY();
		this.ptDir = td.getPtDir();
	}
}