package com.kylin.tankwar.jgroups.handler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kylin.tankwar.core.MainFrame;
import com.kylin.tankwar.jgroups.Communication;
import com.kylin.tankwar.jgroups.Session;
import com.kylin.tankwar.model.Blood;
import com.kylin.tankwar.model.BloodView;
import com.kylin.tankwar.model.Event;
import com.kylin.tankwar.model.Explode;
import com.kylin.tankwar.model.ExplodeView;
import com.kylin.tankwar.model.Missile;
import com.kylin.tankwar.model.MissileView;
import com.kylin.tankwar.model.Tank;
import com.kylin.tankwar.model.TankView;

public class CommHandler implements IHandler {
	
	private static final Logger logger = Logger.getLogger(CommHandler.class);

	/**
	 * Only send myTank status to group members
	 */
	public void sendHandler(Tank tank, Communication comm, Event event) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Send myTank<" + tank.getView() + "> to group members");
		}
		
		Session session = new Session();
		session.setEvent(event);
		session.addTankView(tank.getId(), tank.getView());
		
		// use asych currently
//		comm.asychSend(session);
	}
	
	public void sendHandler(Missile missile, Communication comm, Event event) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Send my missile<" + missile.getMissileView() + "> to group members");
		}
		
		Session session = new Session();
		session.setEvent(event);
		session.addMissileView(missile.getId(), missile.getMissileView());
		
		// use asych currently
//		comm.asychSend(session);
	}
	
	public void sendHandler(Explode explode, Communication comm, Event event) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Send Explode<" + explode.getView() + "> to group members");
		}
		
		Session session = new Session();
		session.setEvent(event);
		session.setExplodeView(explode.getView());
		
		// use asych currently
//		comm.asychSend(session);
	}
	
	public void sendHandler(Blood blood, Communication comm, Event event) {

		if(logger.isDebugEnabled()) {
			logger.debug("Send Blood<" + blood.getBooldView() + "> to group members");
		}
		
		Session session = new Session();
		session.setEvent(event);
		session.setBloodView(blood.getBooldView());
		
		// use asych currently
//		comm.asychSend(session);
	}

	public void recieveHandler(MainFrame mainFrame, Session session, Session rec) {
		
		logBeforeSession(session);
		
		session.merge(rec);
		
/*		for(TankView view : rec.getTankViewMap().values()) {
			
			if(view.isLive()) {*/
				
//				Tank tank = mainFrame.getTankMap().get(view.getId());
//				
//				if(null == tank) {
//					Tank groupTank = new Tank(view);
//					mainFrame.getTankMap().put(view.getId(), groupTank);
//				} else {
//					tank.updateTank(view);
//				}
//			} else {
//				session.removeTankView(view.getId());
//				mainFrame.getTankMap().remove(view.getId());
//			}
//		}
		
		if(rec.getEvent() == Event.TN) {
//			mainFrame.replicateTank(Event.TM);
		}
		
		logAfterSession(session);
	}
	
	public void recieveHandler(Map<String, Missile> missileMap, Session session, Session rec) {

		logBeforeSession(session);
		
		session.merge(rec);
		
		for(MissileView view : rec.getMissileViewMap().values()) {
			
			if(view.isLive()) {
				
				Missile missile = missileMap.get(view.getId());
				
				if(null == missile) {
					Missile groupMissile = new Missile(view);
					missileMap.put(view.getId(), groupMissile);
				} else {
					missile.updateMissile(view);
				}
				
			} else {
				session.romoveMissileView(view.getId());
				missileMap.remove(view.getId());
			}
		}
		
		logAfterSession(session);
	}
	
	public void recieveHandler(List<Explode> explodes, Session session, Session rec) {

		ExplodeView view = rec.getExplodeView();
		
		session.setExplodeView(view);
		
		Explode explode = new Explode(view);
		
		explodes.add(explode);
	}	
	
	
	public void recieveHandler(Blood blood, Session session, Session rec) {

		BloodView view = rec.getBloodView();
		
		session.setBloodView(view);
		
		blood.updateBlood(view);
	}
	
	private void logAfterSession(Session session) {

		if(logger.isDebugEnabled()) {
			logger.debug("debug session, after recieve handler");
			session.logSession();
		}
	}

	private void logBeforeSession(Session session) {

		if(logger.isDebugEnabled()) {
			logger.debug("debug session, before recieve handler");
			session.logSession();
		}
	}
	
	
	
	
	
	
	
	/**
	 * Only send myTank missile to group members.
	 */
	public void sendHandler(String id, Collection<Missile> missiles, Communication comm, Event event) {
		
		if(logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			for(Missile m : missiles) {
				sb.append(m.getMissileView());
				sb.append("\n");
			}
			
			logger.debug("Missiles: \n " + sb.toString());
		}
		
		Session session = new Session();
		session.setEvent(event);
		for(Missile m : missiles){
			if(id.compareTo(m.getTankId()) == 0) {
				session.addMissileView(m.getId(), m.getMissileView());
			}
		}
		
		// use asych currently
//		comm.asychSend(session);
	}

	public void recieveHandler(String id, Map<String, Missile> missileMap, Session session, MainFrame mainFrame) {

		logBeforeSession(session); 
		
		String missileId = null;
		
		for(Entry<String, MissileView> entry : session.getMissileViewMap().entrySet()) {
			
			if(entry.getKey().compareTo(id) == 0) {
				continue ;
			}
			
			if(missileMap.get(entry.getKey()) == null) {
				Missile m = new Missile(entry.getValue(), mainFrame);
				missileMap.put(m.getId(), m);
			} else {
				missileMap.get(entry.getKey()).updateMissile(session.getMissileView(entry.getKey()));
			}
			
			if(entry.getValue().isLive() == false) {
				missileId = entry.getKey();
			}
		}
		
		if(null != missileId) {
//			mainFrame.getMissileMap().remove(missileId);
			session.romoveMissileView(missileId);
		}
		
		logAfterSession(session);
	}

	
	
	public void sendHandler(Missile missile, Tank tank, Communication comm) {

		logger.debug("Sent hit tank info");
		
		Session session = new Session();
		session.setEvent(Event.DEATH);
		session.addMissileView(missile.getId(), missile.getMissileView());
		session.addTankView(tank.getId(), tank.getView());
		
		logger.debug("send tank hit session: \n");
		session.logSession();
	}

	public void recieveHandler(String missileId, String tankId, Session session, MainFrame mainFrame) {
		
		logBeforeSession(session);
		
//		mainFrame.getTankMap().get(tankId).updateTank(session.getTankView(tankId));
//		mainFrame.getMissileMap().remove(missileId);
//		session.romoveMissileView(missileId);
		
		logAfterSession(session);
	}

	

	

	

}
