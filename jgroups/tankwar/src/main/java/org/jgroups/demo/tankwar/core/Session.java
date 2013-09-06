package org.jgroups.demo.tankwar.core;

import java.io.Serializable;

import org.jgroups.demo.tankwar.model.BloodView;
import org.jgroups.demo.tankwar.model.ExplodeView;
import org.jgroups.demo.tankwar.model.MissileView;
import org.jgroups.demo.tankwar.model.TankView;


public class Session implements Serializable {

	private static final long serialVersionUID = -7415952187188878970L;
	
	private TankView tankView;
	
	private BloodView bloodView;
	
	private ExplodeView explodeView;
	
	private MissileView missileView;
	
	private final Type type;

	public Session(TankView tankView, Type type) {
		this.tankView = tankView;
		this.type = type;
	}

	public Session(BloodView bloodView, Type type) {
		this.bloodView = bloodView;
		this.type = type;
	}

	public Session(ExplodeView explodeView, Type type) {
		this.explodeView = explodeView;
		this.type = type;
	}

	public Session(MissileView missileView, Type type) {
		this.missileView = missileView;
		this.type = type;
	}

	public TankView tankView() {
		return tankView;
	}

	public BloodView bloodView() {
		return bloodView;
	}

	public ExplodeView explodeView() {
		return explodeView;
	}

	public MissileView missileView() {
		return missileView;
	}

	public Type type() {
		return type;
	}

	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("Session [type = " + type + ", ");
		if (null != tankView && !"".equals(tankView))
			sb.append("tankView = " + tankView);
		if (null != bloodView && !"".equals(bloodView))
			sb.append("bloodView = " + bloodView);
		if (null != explodeView && !"".equals(explodeView))
			sb.append("explodeView = " + explodeView);
		if (null != missileView && !"".equals(missileView))
			sb.append("missileView = " + missileView);
		sb.append("]");
		
		return sb.toString();
	}
	
	

}
