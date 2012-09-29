package com.kylin.tankwar.jgroups;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

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
	
	
	Map<String, TankView> tankViewMap = new HashMap<String, TankView>();
	
	Map<String, MissileDraw> missileDrawMap = new HashMap<String, MissileDraw>();
	
	Map<String, ExplodeDraw> explodeDrawMap = new HashMap<String, ExplodeDraw>();
	
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
	
	public void logSession() {
		
		if(!logger.isDebugEnabled()) {
			return ;
		}
		
		logger.debug("------------ Log Session Start --------------");
		logger.debug("Total TankDraw size: " + tankViewMap.size());
		for(String key : tankViewMap.keySet()) {
			logger.debug(key + " -> " + tankViewMap.get(key));
		}
		
		//TODO add missiles
		
		// TODO add explodes
		
		logger.debug("------------  Log Session End  --------------");
	}
	
	public void print() {
		
		System.out.println("------------ Print Session Start --------------");
		
		System.out.println("Total TankDraw size: " + tankViewMap.size());
		
		for(String key : tankViewMap.keySet()) {
			System.out.println(key + " -> " + tankViewMap.get(key));
		}
		
		//TODO add missiles
		
		// TODO add explodes
		
		System.out.println("------------  Print Session End  --------------");
	}

	public void merge(Session session) {

		for(String id : session.getTankViewMap().keySet()) {
			addTankView(id, session.getTankView(id));
		}
		
		//TODO add missiles
		
		// TODO add explodes
	}

}
