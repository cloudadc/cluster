package com.kylin.tankwar.jgroups.threads;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.log4j.Logger;
import org.jgroups.Message;

import com.kylin.tankwar.core.Session;
import com.kylin.tankwar.core.Type;
import com.kylin.tankwar.model.Blood;
import com.kylin.tankwar.model.Explode;

public class AsychOtherThread extends ThreadBase implements Runnable {
	
	private static final Logger logger = Logger.getLogger(AsychOtherThread.class);
	
	private ArrayBlockingQueue<Session> queue ;
	
	private List<Explode> explodes ;
	
	private List<Blood> bloods; 
	
	public AsychOtherThread(ArrayBlockingQueue<Session> queue, List<Explode> explodes, List<Blood> bloods, String name, String cluster, String jgroupsProps) {
		super(name, cluster, jgroupsProps);
		this.queue = queue;
		this.explodes = explodes ;
		this.bloods = bloods;
	}

	public void receive(Message msg) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("handle message, " + msg.printHeaders() + " | " + msg.getSrc()  + " | "  + msg.getObject()) ;
		}
				
		Session session = (Session) msg.getObject();
		
		if(session.type().equals(Type.B) ) {
			bloods.get(0).updateBlood(session.bloodView());
		} else if(session.type().equals(Type.E)) {
			explodes.add(new Explode(session.explodeView()));
		}
		
	}

	public void run() {
		
		Thread.currentThread().setName("TankWar-Asych-Other");
		
		logger.info("Start Running");
		
		while(true) {
			
			try {
				Session session = queue.take();
				
				if(logger.isDebugEnabled()){
					logger.debug("send message " + session + ", queue size =" + queue.size() );
				}
								
				channel.send(null, session);
				
//				if(session.type().equals(Type.B) && isCoordinator()){
//					channel.send(null, session);
//				} else {
//					channel.send(null, session);
//				}
			} catch (Exception e) {
				throw new TankWarThreadException("OtherThread Error", e);
			}
		}
	}

}
