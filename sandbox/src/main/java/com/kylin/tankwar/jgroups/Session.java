package com.kylin.tankwar.jgroups;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kylin.tankwar.core.Event;
import com.kylin.tankwar.core.ExplodeView;
import com.kylin.tankwar.core.MissileView;
import com.kylin.tankwar.core.TankView;

public class Session implements Serializable {

	private static final long serialVersionUID = 6954018317301039760L;
	
	private static final Logger logger = Logger.getLogger(Session.class);
	
	private String nodeName;
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	private Event event ;
	
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	Map<String, TankView> tankViewMap = new HashMap<String, TankView>();
	
	Map<String, ExplodeDraw> explodeDrawMap = new HashMap<String, ExplodeDraw>();

	ExplodeView explodeView;
	
	public void addTankView(String key, TankView value) {
		tankViewMap.put(key, value);
	}

	public TankView getTankView(String key) {
		return tankViewMap.get(key);
	}
	
	public void removeTankView(String key) {
		tankViewMap.remove(key);
	}
	
	public Set<String> tankIdSet() {
		return tankViewMap.keySet();
	}

	public Map<String, TankView> getTankViewMap() {
		return tankViewMap;
	}
	
	Map<String, MissileView> missileViewMap = new HashMap<String, MissileView>();
	
	public void addMissileView(String key, MissileView value) {
		missileViewMap.put(key, value);
	}
	
	public MissileView getMissileView(String key) {
		return missileViewMap.get(key);
	}
	
	public void romoveMissileView(String key) {
		missileViewMap.remove(key);
	}
	
	public Set<String> missileIdSet() {
		return missileViewMap.keySet();
	}
	
	public Map<String, MissileView> getMissileViewMap() {
		return missileViewMap ;
	}
	
	public ExplodeView getExplodeView() {
		return explodeView;
	}

	public void setExplodeView(ExplodeView explodeView) {
		this.explodeView = explodeView;
	}

	public void logSession() {
		
		if(!logger.isDebugEnabled()) {
			return ;
		}
		
		logger.debug("------------ Log Session Start --------------");
		
		logger.debug("Total TankView size: " + tankViewMap.size());
		for(String key : tankViewMap.keySet()) {
			logger.debug(key + " -> " + tankViewMap.get(key));
		}
		
		logger.debug("Total MissileView size: " + missileViewMap.size());
		for(String key : missileViewMap.keySet()) {
			logger.debug(key + " -> " + missileViewMap.get(key));
		}
		
		// TODO add explodes
		
		logger.debug("------------  Log Session End  --------------");
	}
	
	public void print() {
		
		System.out.println("------------ Print Session Start --------------");
		
		System.out.println("Total TankView size: " + tankViewMap.size());
		
		for(String key : tankViewMap.keySet()) {
			System.out.println(key + " -> " + tankViewMap.get(key));
		}
		
		System.out.println("Total MissileView size: " + missileViewMap.size());
		
		for(String key : missileViewMap.keySet()) {
			System.out.println(key + " -> " + missileViewMap.get(key));
		}
		
		// TODO add explodes
		
		System.out.println("------------  Print Session End  --------------");
	}

	public void merge(Session session) {

		for(String id : session.getTankViewMap().keySet()) {
			addTankView(id, session.getTankView(id));
		}
		
		for(String id : session.getMissileViewMap().keySet()) {
			addMissileView(id, session.getMissileView(id));
		}
		
		// TODO add explodes
	}

}
