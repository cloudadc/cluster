package com.kylin.tankwar.jgroups.threads;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.jgroups.Message;

import com.kylin.tankwar.core.Session;
import com.kylin.tankwar.model.Tank;
import com.kylin.tankwar.model.TankView;


public class AsychTankThread extends ThreadBase implements Runnable {
	
	private static final Logger logger = Logger.getLogger(AsychTankThread.class);
	
	private ArrayBlockingQueue<Session> queue ;
	
	private Map<String,Tank> tankMap;

	public AsychTankThread(ArrayBlockingQueue<Session> queue, Map<String,Tank> tankMap, String name, String cluster, String jgroupsProps) {
		super(name, cluster, jgroupsProps);
		this.queue = queue;
		this.tankMap = tankMap;
	}

	public void receive(Message msg) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("handle message, " + msg.printHeaders() + " | " + msg.getSrc() + " | " + msg.toString()) ;
		}
		
		Session session = (Session) msg.getObject();
		TankView view = session.tankView();
		if(view.isLive()){
			Tank tank = tankMap.get(view.getId());
			if(null == tank) {
				tankMap.put(view.getId(), new Tank(view));
			} else {
				tank.updateTank(view);
			}
		} else {
			tankMap.remove(view.getId());
		}
		
	}

	public void run() {
		
		Thread.currentThread().setName("TankWar-Asych-Tank");
		
		logger.info("Start Running");
				
		while(true) {
			
			try {
				Session session = queue.take();
				
				if(logger.isDebugEnabled()){
					logger.debug("send message " + session + ", queue size =" + queue.size() + ", tankMap size = " + tankMap.size());
				}
				
				channel.send(null, session);
			} catch (Exception e) {
				throw new TankWarThreadException("TankThread Error", e);
			}
		}
	}

}
