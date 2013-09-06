package org.jgroups.demo.tankwar.core;

import org.jgroups.demo.tankwar.model.BloodView;
import org.jgroups.demo.tankwar.model.ExplodeView;
import org.jgroups.demo.tankwar.model.MissileView;
import org.jgroups.demo.tankwar.model.TankView;

public interface IReplication {

	public void replicateTank(TankView view);
	
	public void replicateBlood(BloodView view);
	
	public void replicateExplode(ExplodeView view);
	
	public void replicateMissile(MissileView view);
}
