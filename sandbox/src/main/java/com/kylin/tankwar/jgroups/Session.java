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
	
	/**
	 * This for keep remove exit node related draw instance
	 */
//	Map<String, Set<String>> nodeMap = new HashMap<String, Set<String>>();
//	
//	public void addNodeMapping(Session session) {
//		
//		Set<String> set = nodeMap.get(session.getNodeName());
//		
//		if(set == null) {
//			set = new HashSet<String>();
//		}
//		
//		set.addAll(session.getAllID());
//	}
//	
//	public Set<String> getAllNodeID() {
//		return nodeMap.keySet();
//	}
//
//	private Set<String> getAllID() {
//		
//		Set<String> set = new HashSet<String>();
//		set.addAll(tankDrawMap.keySet());
//		set.addAll(missileDrawMap.keySet());
//		set.addAll(explodeDrawMap.keySet());
//		return set;
//	}

	
	Map<String, TankView> tankDrawMap = new HashMap<String, TankView>();
	
	Map<String, MissileDraw> missileDrawMap = new HashMap<String, MissileDraw>();
	
	Map<String, ExplodeDraw> explodeDrawMap = new HashMap<String, ExplodeDraw>();
	
	public void addTankDraw(String key, TankView value) {
		tankDrawMap.put(key, value);
	}

	public TankView getTankDraw(String key) {
		return tankDrawMap.get(key);
	}
	
	public void removeTankDraw(String key) {
		tankDrawMap.remove(key);
	}

	public Map<String, TankView> getTankDrawMap() {
		return tankDrawMap;
	}
	
	public void logSession() {
		
		if(!logger.isDebugEnabled()) {
			return ;
		}
		
		logger.debug("------------ Log Session Start --------------");
		logger.debug("Total TankDraw size: " + tankDrawMap.size());
		for(String key : tankDrawMap.keySet()) {
			logger.debug(key + " -> " + tankDrawMap.get(key));
		}
		
		//TODO add missiles
		
		// TODO add explodes
		
		logger.debug("------------  Log Session End  --------------");
	}
	
	public void print() {
		
		System.out.println("------------ Print Session Start --------------");
		
		System.out.println("Total TankDraw size: " + tankDrawMap.size());
		
		for(String key : tankDrawMap.keySet()) {
			System.out.println(key + " -> " + tankDrawMap.get(key));
		}
		
		//TODO add missiles
		
		// TODO add explodes
		
		System.out.println("------------  Print Session End  --------------");
	}

	public void merge(Session session) {

		for(String id : session.getTankDrawMap().keySet()) {
			addTankDraw(id, session.getTankDraw(id));
		}
		
		//TODO add missiles
		
		// TODO add explodes
	}

}
