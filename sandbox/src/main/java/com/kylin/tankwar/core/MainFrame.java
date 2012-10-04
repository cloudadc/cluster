package com.kylin.tankwar.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.kylin.tankwar.jgroups.AsychCommunication;
import com.kylin.tankwar.jgroups.Communication;
import com.kylin.tankwar.jgroups.handler.CommHandler;

public class MainFrame extends Frame {

	private static final long serialVersionUID = 8165439060971008330L;
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	
	private Communication comm;
	
	public Communication getComm() {
		return comm;
	}

	CommHandler handler = new CommHandler();
	
	public CommHandler getHandler() {
		return handler;
	}

	private Tank myTank ;
	
	public Tank getMyTank() {
		return myTank;
	}
	
	private boolean isGood ;
	
	Wall w1 = new Wall(100, 200, 20, 200);
	Wall w2 = new Wall(300, 100, 200, 20);
	Wall w3 = new Wall(650, 200, 20, 200);
	Wall w4 = new Wall(300, 500, 200, 20);
	
	private Image offScreenImage = null;
	
	Map<String,Tank> tankMap = new ConcurrentHashMap<String,Tank>();

	public Map<String, Tank> getTankMap() {
		return tankMap;
	}
	
	Map<String, Missile> missileMap = new ConcurrentHashMap<String, Missile>();
	Set<Missile> myMissiles = new HashSet<Missile>();
	

	public Map<String, Missile> getMissileMap() {
		return missileMap;
	}
	
//	private boolean isReplicateTank = true ;
//	private boolean isReplicateMissile = true ;
//	private boolean isReplicateExplode = true ;

	public MainFrame() {
		
	}
	
	public MainFrame(String props, String name) {
		
		logger.info("initialize  MainFrame");
		
		initComm(props, name);
		
		initTank();
		
		launchFrame();

	}

	private void initTank() {
		
		String id = comm.getChannelName();
		
		if(comm != null && comm.getMemberSize() % 2 == 0) {
			isGood = false ;
		} else {
			isGood = true ;
		}
		
		int x = getRandom(GAME_WIDTH - 100);
		int y = getRandom(GAME_HEIGHT - 100);
		
		myTank = new Tank(id, isGood, true, 100, x, y, Direction.STOP, Direction.D, this);
		tankMap.put(id, myTank);
		logger.info("initialized a Tank, " + myTank.getView());
		
		replicateTank(Event.TN);
	}
	
	public int getRandom(int max) {
		
		int r = new Random().nextInt(max);
		
		if(r > 50) {
			return r;
		} else {
			return getRandom(max);
		}
	}

	private void initComm(String props, String name) {
		
		logger.info("initialize communication");
		
		comm = new AsychCommunication(this);
		
		comm.connect(props, name);
	}
	
	private void launchFrame() {
		
		logger.info("launch Frame Start");
		
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		
		String name = "TankWar";
		if(null != comm) {
			name = name + " - " + comm.getChannelName();
		}
		this.setTitle(name);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myTank.setLive(false);
				replicateTank(Event.TM);
				
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
	
	public void replicateTank(Event event) {
		handler.sendHandler(myTank, comm, event);
	}
	
	public void replicateMissile(Event event) {
		handler.sendHandler(myTank.getId(), missileMap.values(), comm, event);
	}
	
	public void paint(Graphics g) {
		
		g.drawString("tanks count: " + tankMap.keySet().size() + ", missiles count: " + missileMap.size(), 10, 80);
		g.drawString("tank     life:" + myTank.getLife(), 10, 100);
		
		for(Tank tank : tankMap.values()) {
			tank.draw(g);
			
			if(myTank.getId().compareTo(tank.getId()) != 0 && myTank.getRect().intersects(tank.getRect())) {
				
				myTank.relocate();
				
				if(myTank.isGood() != tank.isGood()) {
					myTank.setLife(myTank.getLife() - 20);
				}
				
				replicateTank(Event.TM);
			}
		}
			
		for(Missile missile : missileMap.values()) {
			
			missile.hitWall(w1);
			missile.hitWall(w2);
			missile.hitWall(w3);
			missile.hitWall(w4);
			
			missile.hitTank(myTank);
			
			missile.draw(g);
			
		}
		
		w1.draw(g);
		w2.draw(g);
		w3.draw(g);
		w4.draw(g);
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

	public static void main(String[] args) {

		MainFrame main = new MainFrame();
		main.initTank();
		main.launchFrame();
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
