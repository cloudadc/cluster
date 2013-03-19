package com.kylin.tankwar.jgroups.threads;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.jgroups.Message;

import com.kylin.tankwar.core.Session;
import com.kylin.tankwar.model.Missile;
import com.kylin.tankwar.model.MissileView;

public class AsychMissileThread extends ThreadBase implements Runnable {
	
	private static final Logger logger = Logger.getLogger(AsychMissileThread.class);
	
	private ArrayBlockingQueue<Session> queue ;
	
	private Map<String, Missile> missileMap ;
	
	public AsychMissileThread(ArrayBlockingQueue<Session> queue, Map<String, Missile> missileMap, String name, String cluster, String jgroupsProps) {
		super(name, cluster, jgroupsProps);
		this.queue = queue;
		this.missileMap = missileMap;
	}

	public void receive(Message msg) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("handle message, " + msg.printHeaders() + " | " + msg.getSrc()  + " | "  + msg.getObject()) ;
		}
		
		Session session = (Session) msg.getObject();
		MissileView view = session.missileView();
		if(view.isLive()) {
			Missile missile = missileMap.get(view.getId());
			if(null == missile) {
				missileMap.put(view.getId(), new Missile(view));
			} else {
				missile.updateMissile(view);
			}
		} else {
			missileMap.remove(view.getId());
		}
	}

	public void run() {

		Thread.currentThread().setName("TankWar-Asych-Missile");
		
		logger.info("Start Running");
		
		while(true) {
			
			try {
				Session session = queue.take();
				
				if(logger.isDebugEnabled()){
					logger.debug("send message " + session + ", queue size =" + queue.size() + ", missileMap size = " + missileMap.size());
				}
				
				channel.send(null, session);
			} catch (Exception e) {
				throw new TankWarThreadException("TankThread Error", e);
			}
		}
	}

}
