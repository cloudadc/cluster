package com.kylin.tankwar.core;

import java.io.Serializable;

import com.kylin.tankwar.model.BloodView;
import com.kylin.tankwar.model.ExplodeView;
import com.kylin.tankwar.model.MissileView;
import com.kylin.tankwar.model.TankView;

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
	
	

}
