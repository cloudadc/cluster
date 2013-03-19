package com.kylin.tankwar.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import org.apache.log4j.Logger;

import com.kylin.tankwar.jgroups.Communication;
import com.kylin.tankwar.model.Blood;
import com.kylin.tankwar.model.Direction;
import com.kylin.tankwar.model.Explode;
import com.kylin.tankwar.model.Missile;
import com.kylin.tankwar.model.Tank;
import com.kylin.tankwar.model.Wall;

public class MainFrame extends Frame {

	private static final long serialVersionUID = 8165439060971008330L;
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	
	private Communication comm;
	
	public Communication getComm() {
		return comm;
	}
	
	Wall w1 = new Wall(100, 200, 20, 200);
	Wall w2 = new Wall(300, 100, 200, 20);
	Wall w3 = new Wall(650, 200, 20, 200);
	Wall w4 = new Wall(300, 500, 200, 20);
	
	private Image offScreenImage = null;
	
	Tank myTank ;

	Explode myExplode = null;
	
	Blood blood = null;

	public MainFrame(Communication comm, boolean isGood) {
		
		this.comm = comm ;
		initTank(isGood);	
		initBlood();
		launchFrame();
		
		logger.info("initialize  MainFrame");
	}

	private void initBlood() {
		blood = new Blood(this);
		comm.setBlood(blood);
		comm.replicateBlood(blood.getBooldView());
	}

	private void initTank(boolean isGood) {
		
		String id = comm.getName();
		
		int x = getRandom(GAME_WIDTH - 100);
		int y = getRandom(GAME_HEIGHT - 100);
		
		myTank = new Tank(id, isGood, true, 100, x, y, Direction.STOP, Direction.D, this);
		
		comm.put(id, myTank);		
		comm.replicateTank(myTank.getView());
	}
	
	public int getRandom(int max) {
		
		int r = new Random().nextInt(max);
		
		if(r > 50) {
			return r;
		} else {
			return getRandom(max);
		}
	}

	private void launchFrame() {
		
		logger.info("launch Frame Start");
		
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
	
		this.setTitle("TankWar" + " - " + comm.getName());
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myTank.setLive(false);
				comm.replicateTank(myTank.getView());
				
				new Thread(new Runnable(){

					public void run() {
						try {
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						System.exit(1);
					}}).start();
			}
		});
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		
		setVisible(true);
		
		this.addKeyListener(new KeyMonitor());
		
		new Thread(new PaintThread()).start();
	}
	
	public void paint(Graphics g) {
		
		g.drawString("Tanks count: " + comm.getMemberSize()+ ", Missiles count: " + comm.getMissileMap().size() +  ", Rank: 1", 10, 50);
				
		for(Missile missile : comm.getMissileMap().values()) {
			if(missile.getTankId().compareTo(myTank.getId()) == 0) {
				hitWall(missile) ;
				missile.hitTank(comm.getTankMap().values());
				missile.draw(g, true);
			} else {
				
				if(missile.hitTank(myTank)) {
					comm.replicateTank(myTank.getView());
				}
				missile.draw(g, false);
			}				
		}

		for(Tank tank : comm.getTankMap().values()) {
			
			if(!tank.isLive()) {
				comm.getTankMap().remove(tank.getId());
				continue;
			} else if(myTank.getId().compareTo(tank.getId()) == 0 && myTank.getLife() > 0 && myTank.getRect().intersects(blood.getRect()) && blood.isLive()) {
				myTank.setLife(100);
				comm.replicateTank(myTank.getView());
				blood.setLive(false);
				comm.replicateBlood(blood.getBooldView());
			}else if(myTank.getId().compareTo(tank.getId()) == 0 && myTank.getLife() <= 0) {
				myTank.setLive(false);
				comm.replicateTank(myTank.getView());
				continue;
			} else if(myTank.getId().compareTo(tank.getId()) != 0 && myTank.getRect().intersects(tank.getRect())) {

				myTank.relocate();
				
				if(myTank.isGood() != tank.isGood()) {
					myTank.setLife(myTank.getLife() - 20);
				}
				
				comm.replicateTank(myTank.getView());
			}
			
			comm.getBlood().draw(g);
			
			tank.draw(g);
		}
		
		if(!myTank.isLive() && myTank.isExplode()){
			myExplode = new Explode(myTank.getX(), myTank.getY(), myTank.getId());
			comm.replicateExplode(myExplode.getView());
			myTank.setExplode(false);
		}
		
		paintExplode(g);
		
		paintWall(g);
	}
	
	
	private void paintExplode(Graphics g) {

		if(null != myExplode) {
			
			if(myExplode.isLive()) {
				myExplode.draw(g);
			} else {
				myExplode = null;
			}
		}
		
		int size = comm.getExplodes().size();
		for(int i = 0 ; i < size ; i ++ ) {
			Explode e = comm.getExplodes().get(i);
			e.draw(g);
			
			if(!e.isLive()) {
				comm.getExplodes().remove(i);
			}
		}
		
	}

	private void paintWall(Graphics g) {
		
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
	}

	private boolean hitWall(Missile missile) {
		
		return missile.hitWall(w1) || missile.hitWall(w2) || missile.hitWall(w3) || missile.hitWall(w4);
	}

	public void update(Graphics g) {
				
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.LIGHT_GRAY);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter  {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
	}

}
