package com.kylin.tankwar.jgroups.handler;

import java.util.Collection;
import java.util.Map;

import com.kylin.tankwar.core.Event;
import com.kylin.tankwar.core.MainFrame;
import com.kylin.tankwar.core.Missile;
import com.kylin.tankwar.core.Tank;
import com.kylin.tankwar.jgroups.Communication;
import com.kylin.tankwar.jgroups.Session;

public interface IHandler {

	public void sendHandler(Tank tank, Communication comm, Event event);
	
	public void sendHandler(Missile missile, Communication comm, Event event);
	
	public void recieveHandler(MainFrame mainFrame, Session session, Session rec);
	
	public void recieveHandler(Map<String, Missile> missileMap, Session session, Session rec);
	
	
	
	
	public void sendHandler(String id, Collection<Missile> missiles, Communication comm, Event event);
	
	public void recieveHandler(String id, Map<String, Missile> missileMap, Session session, MainFrame mainFrame);

	/**
	 *  For death missile
	 */
	public void sendHandler(Missile missile, Tank tank, Communication comm);
	
	/**
	 *  For death missile
	 */
	public void recieveHandler(String missileId, String tankId, Session session, MainFrame mainFrame);
	
	
}
