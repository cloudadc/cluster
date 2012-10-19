package com.kylin.tankwar.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.kylin.tankwar.jgroups.AsychCommunication;
import com.kylin.tankwar.jgroups.Communication;
import com.kylin.tankwar.jgroups.handler.CommHandler;
import com.kylin.tankwar.jgroups.handler.IHandler;

public class MainFrame extends Frame {

	private static final long serialVersionUID = 8165439060971008330L;
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	
	private Communication comm;
	
	public Communication getComm() {
		return comm;
	}

	IHandler handler = new CommHandler();
	
	public IHandler getHandler() {
		return handler;
	}
	
	private boolean isGood ;
	
	Wall w1 = new Wall(100, 200, 20, 200);
	Wall w2 = new Wall(300, 100, 200, 20);
	Wall w3 = new Wall(650, 200, 20, 200);
	Wall w4 = new Wall(300, 500, 200, 20);
	
	private Image offScreenImage = null;
	
	
	Tank myTank ;
	Map<String,Tank> tankMap = new ConcurrentHashMap<String,Tank>();

	public Map<String, Tank> getTankMap() {
		return tankMap;
	}
	
	public Tank getMyTank() {
		return myTank;
	}
	
	Map<String, Missile> missileMap = new ConcurrentHashMap<String, Missile>();
	

	public Map<String, Missile> getMissileMap() {
		return missileMap;
	}
	
	Explode myExplode = null;
	List<Explode> explodes = new ArrayList<Explode>();

	public List<Explode> getExplodes() {
		return explodes;
	}
	
	Blood blood = new Blood(this);
	
	public Blood getBlood() {
		return blood ;
	}

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
		replicateBlood(Event.B);
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
	
	@Deprecated
	public void replicateMissile(Event event) {
		handler.sendHandler(myTank.getId(), missileMap.values(), comm, event);
	}
	
	public void replicateExplode(Event event) {
		handler.sendHandler(myExplode, comm, event);
	}
	
	public void replicateBlood(Event event) {
		handler.sendHandler(blood, comm, event);
	}
	
	public void replicateMissile(Missile missile, Event event) {
		handler.sendHandler(missile, comm, event);
	}
	
	/**
	 *  keep each time death missiles
	 */
	Vector<String> vactor = new Vector<String>(2, 2);
	
	public void paint(Graphics g) {
		
		g.drawString("Tanks count: " + tankMap.keySet().size() + ", Missiles count: " + missileMap.size() + ", Life: " + myTank.getLife() + ", Rank: 1", 10, 50);
		
		vactor.clear();
		
		for(Missile missile : missileMap.values()) {
			
			if(missile.getTankId().compareTo(myTank.getId()) == 0) {
				
				if(hitWall(missile) || missile.hitTank(getTankMap().values())){
					vactor.add(missile.getId());
				}
				
				missile.draw(g, true);
			} else {
				
				if(missile.hitTank(myTank)) {
					replicateTank(Event.TM);
				}
				
				missile.draw(g, false);
			}				
		}
		
		for(String id : vactor) {
			missileMap.remove(id);
			getComm().getSession().romoveMissileView(id);
		}
		
		for(Tank tank : tankMap.values()) {
			
			if(!tank.isLive()) {
				continue;
			} else if(myTank.getId().compareTo(tank.getId()) == 0 && myTank.getLife() > 0 && myTank.getRect().intersects(blood.getRect()) && blood.isLive()) {
				myTank.setLife(100);
				replicateTank(Event.TM);
				blood.setLive(false);
				replicateBlood(Event.B);
			}else if(myTank.getId().compareTo(tank.getId()) == 0 && myTank.getLife() <= 0) {
				myTank.setLive(false);
				replicateTank(Event.TM);
				continue;
			} else if(myTank.getId().compareTo(tank.getId()) != 0 && myTank.getRect().intersects(tank.getRect())) {

				myTank.relocate();
				
				if(myTank.isGood() != tank.isGood()) {
					myTank.setLife(myTank.getLife() - 20);
				}
				
				replicateTank(Event.TM);
			}
			
			blood.draw(g);
			
			tank.draw(g);
		}
		
		if(!myTank.isLive() && myTank.isExplode()){
			myExplode = new Explode(myTank.getX(), myTank.getY(), myTank.getId());
			replicateExplode(Event.EM);
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
		
		for(int i = 0 ; i < getExplodes().size() ; i ++ ) {
			Explode e = explodes.get(i);
			e.draw(g);
			
			if(!e.isLive()) {
				explodes.remove(i);
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
