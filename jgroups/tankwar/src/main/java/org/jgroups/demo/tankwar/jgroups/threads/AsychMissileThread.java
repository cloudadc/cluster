package org.jgroups.demo.tankwar.jgroups.threads;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.jgroups.Message;
import org.jgroups.demo.tankwar.core.Session;
import org.jgroups.demo.tankwar.model.Missile;
import org.jgroups.demo.tankwar.model.MissileView;


public class AsychMissileThread extends ThreadBase implements Runnable {
	
	
	private ArrayBlockingQueue<Session> queue ;
	
	private Map<String, Missile> missileMap ;
	
	public AsychMissileThread(ArrayBlockingQueue<Session> queue, Map<String, Missile> missileMap, String name, String cluster, String jgroupsProps) {
		super(name, cluster, jgroupsProps);
		this.queue = queue;
		this.missileMap = missileMap;
	}

	public void receive(Message msg) {

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
				
		while(true) {
			
			try {
				Session session = queue.take();
				
				channel.send(null, session);
			} catch (Exception e) {
				throw new TankWarThreadException("TankThread Error", e);
			}
		}
	}

}
