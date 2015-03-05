package org.jgroups.demo.tankwar.jgroups.threads;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.jgroups.Message;
import org.jgroups.demo.tankwar.core.Session;
import org.jgroups.demo.tankwar.core.Type;
import org.jgroups.demo.tankwar.model.Blood;
import org.jgroups.demo.tankwar.model.Explode;


public class AsychOtherThread extends ThreadBase implements Runnable {
		
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
				
		Session session = (Session) msg.getObject();
		
		if(session.type().equals(Type.B) ) {
			bloods.get(0).updateBlood(session.bloodView());
		} else if(session.type().equals(Type.E)) {
			explodes.add(new Explode(session.explodeView()));
		}
		
	}

	public void run() {
		
		Thread.currentThread().setName("TankWar-Asych-Other");
				
		while(true) {
			
			try {
				Session session = queue.take();
										
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
