package com.kylin.tankwar.jgroups;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kylin.tankwar.Explode;
import com.kylin.tankwar.Missile;
import com.kylin.tankwar.Tank;

public class Session implements Serializable {

	private static final long serialVersionUID = 6954018317301039760L;

	private List<Explode> explodes ;
	
	private List<Missile> missiles ;
	
	private List<Tank> tanks ;
	
	public Session(List<Tank> tanks, List<Missile> missiles, List<Explode> explodes) {
		this.tanks = tanks;
		this.missiles = missiles;
		this.explodes = explodes;
	}

	public List<Explode> getExplodes() {
		return explodes;
	}

	public void setExplodes(List<Explode> explodes) {
		this.explodes = explodes;
	}

	public List<Missile> getMissiles() {
		return missiles;
	}

	public void setMissiles(List<Missile> missiles) {
		this.missiles = missiles;
	}

	public List<Tank> getTanks() {
		return tanks;
	}

	public void setTanks(List<Tank> tanks) {
		this.tanks = tanks;
	}
	

}
