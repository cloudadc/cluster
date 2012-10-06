package com.kylin.tankwar;

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

import org.apache.log4j.Logger;

import com.kylin.tankwar.core.Blood;
import com.kylin.tankwar.core.Direction;
import com.kylin.tankwar.core.TankView;
import com.kylin.tankwar.jgroups.Session;
import com.kylin.tankwar.jgroups.SynchCommunication;



public class TankFrame extends Frame {

	private static final long serialVersionUID = 6719091451757240088L;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	private static final Logger logger = Logger.getLogger(TankFrame.class);
	
	Tank_ myTank = new Tank_(50, 50, true, Direction.STOP, this);
	
	Wall_ w1 = new Wall_(100, 200, 20, 150, this);
	Wall_ w2 = new Wall_(300, 100, 300, 20, this);
	
	List<Explode_> explodes = new ArrayList<Explode_>();
	List<Missile_> missiles = new ArrayList<Missile_>();
	List<Tank_> tanks = new ArrayList<Tank_>();
	Image offScreenImage = null;
	
	Blood b = new Blood();
	
	SynchCommunication comm = new SynchCommunication(null);

	public void paint(Graphics g) {
		
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks    count:" + tanks.size(), 10, 90);
		g.drawString("tanks     life:" + myTank.getLife(), 10, 110);
		
		logger.trace("missiles count:" + missiles.size());
		logger.trace("explodes count:" + explodes.size());
		logger.trace("tanks    count:" + tanks.size());
		logger.trace("tanks     life:" + myTank.getLife());
		
		if (tanks.size() <= 0) {
			for (int i = 0; i < 5 ; i++) {
				tanks.add(new Tank_(50 + 40 * (i + 1), 50, false, Direction.D, this));
			}
		}
		
		// currently use synchronous mechanism replicate session
		Session session = comm.synchSend(getOriginSession());
		updateSession(session);
		
		
		for (int i = 0; i < missiles.size(); i++) {
			Missile_ m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
			//if(!m.isLive()) missiles.remove(m);
			//else m.draw(g);
		}
		
		for (int i = 0; i < explodes.size(); i++) {
			Explode_ e = explodes.get(i);
			e.draw(g);
		}
		
		for (int i = 0; i < tanks.size(); i++) {
			Tank_ t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		myTank.draw(g);
		myTank.eat(b);
		w1.draw(g);
		w2.draw(g);
		b.draw(g);
	}
	
	private Session getOriginSession() {
		
		logger.debug("get original  Session");
		
		Session session = new Session();
		
		for (int i = 0 ; i < tanks.size() ; i ++) {
			session.addTankView(tanks.get(i).getId(), tanks.get(i).getTankDraw());
		}
		
		//TODO add missiles
		
		//TODO add explodes
		
		return session;
	}
	
	/**
	 *  This is for synchronous response update
	 * @param session
	 */
	private void updateSession(Session session) {
		
		logger.debug("update Session");
		
		for (int i = 0 ; i < tanks.size() ; i ++) {
			Tank_ t = tanks.get(i);
			t.updateTank(session.getTankView(t.getId()));
			session.removeTankView(t.getId());
		}
		
		for(String id : session.getTankViewMap().keySet()) {
			TankView td = session.getTankView(id);
			Tank_ t = new Tank_(td.getX(), td.getY(), td.isGood(), td.getDir(), null);
			t.setId(id);
			tanks.add(t);
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
	
	public void launchComm(String[] args) {
		
		logger.info("launch communication Start");
		
		String props = "udp.xml";
		String name = null;
		
		for(int i=0; i < args.length; i++) {

			if (args[i].equals("-p")) {
				props = args[++i];
				continue;
			}

			if (args[i].equals("-n")) {
				name = args[++i];
				continue;
			}

			exit();
		}
		
		comm.connect(props, name);
	}
	
	private static void exit() {

		System.out.println("Run Application with [-p <props>] [-n <name>] ");

		System.exit(1);
	}
	
	public void launchFrame() {
	
		logger.info("launch Frame Start");
		
//		int initTankCount = Integer.parseInt(PropertyMgr.getProperty("initTankCount"));
//		logger.info("add enemy Tank number is: " + initTankCount);
//		
//		for(int i=0; i<initTankCount; i++) {
//			tanks.add(new Tank(50 + 40*(i+1), 50, false, Direction.D, this));
//		}
		
		//this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.LIGHT_GRAY);
		
		this.addKeyListener(new KeyMonitor());
		
		this.setVisible(true);
		
		new Thread(new PaintThread()).start();
		
	}

	public static void main(String[] args) {
		TankFrame tc = new TankFrame();
		tc.launchComm(args);
		tc.launchFrame();
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













