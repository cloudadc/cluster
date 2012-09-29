package com.kylin.tankwar.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.kylin.tankwar.Direction;
import com.kylin.tankwar.jgroups.AsychCommunication;
import com.kylin.tankwar.jgroups.Communication;
import com.kylin.tankwar.jgroups.Session;

public class MainFrame extends Frame {

	private static final long serialVersionUID = 8165439060971008330L;
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private static final Logger logger = Logger.getLogger(MainFrame.class);
	
	private Communication comm;
	
	private Tank myTank ;
	
	private boolean isGood ;
	
	private Image offScreenImage = null;
	
	Map<String,Tank> tankMap = new HashMap<String,Tank>();
	
	public MainFrame() {
		
	}
	
	public MainFrame(String props, String name) {
		
		logger.info("initialize  MainFrame");
		
		initComm(props, name);
		
		initTank();
		
		launchFrame();
	}

	private void initTank() {
		
		String id = UUID.randomUUID().toString();
		
		if(comm != null && comm.getMemberSize() % 2 == 0) {
			isGood = false ;
		} else {
			isGood = true ;
		}
		
		int x = getRandom(GAME_WIDTH - 100);
		int y = getRandom(GAME_HEIGHT - 100);
		
		myTank = new Tank(id, isGood, true, 100, x, y, Direction.STOP, Direction.D);
		tankMap.put(id, myTank);
		logger.info("initialized a Tank, " + myTank.getView());
	}
	
	

	private int getRandom(int max) {
		
		int r = new Random().nextInt(max);
		
		if(r > 50) {
			return r;
		} else {
			return getRandom(max);
		}
	}

	private void initComm(String props, String name) {
		
		logger.info("initialize communication");
		
		comm = new AsychCommunication();
		
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
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		
		setVisible(true);
		
		this.addKeyListener(new KeyMonitor());
		
		new Thread(new PaintThread()).start();
	}
	
	
	public void paint(Graphics g) {
		
		g.drawString("tanks    count:" + tankMap.keySet().size(), 10, 90);
		g.drawString("tanks     life:" + myTank.getLife(), 10, 110);
		
		sendSession();
		
		receiveSession();
		
		for(Tank tank : tankMap.values()) {
			tank.draw(g);
		}
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



	private void sendSession() {

		logger.debug("Send session");
		
		Session session = new Session();
		
		for(Tank tank : tankMap.values()) {
			session.addTankView(tank.getId(), tank.getView());
		}
		
		//TODO
		
		//TODO
		
		comm.asychSend(session);
	}

	private void receiveSession() {
		
		logger.debug("Receive Session");
		
		Session session = comm.getSession();
		
		Set<String> tankIds = tankMap.keySet();
		for(String id : session.tankIdSet()) {
			if(tankIds.contains(id)) {
				tankMap.get(id).updateTank(session.getTankView(id));
			} else {
				tankMap.put(id, new Tank(session.getTankView(id)));
			}
		}
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
