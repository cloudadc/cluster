package com.kylin.tankwar.jgroups;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Session implements Serializable {

	private static final long serialVersionUID = 6954018317301039760L;
	
	Map<String, TankDraw> tankDrawMap = new HashMap<String, TankDraw>();
	
	public void addTankDraw(String key, TankDraw value) {
		tankDrawMap.put(key, value);
	}

	public TankDraw getTankDraw(String key) {
		return tankDrawMap.get(key);
	}
	
	public void removeTankDraw(String key) {
		tankDrawMap.remove(key);
	}

	public Map<String, TankDraw> getTankDrawMap() {
		return tankDrawMap;
	}

	public void merge(Session session) {

		for(String id : session.getTankDrawMap().keySet()) {
			addTankDraw(id, session.getTankDraw(id));
		}
		
		//TODO add missiles
		
		// TODO add explodes
	}

}
