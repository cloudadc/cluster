package com.kylin.tankwar.core;

import com.kylin.tankwar.model.BloodView;
import com.kylin.tankwar.model.ExplodeView;
import com.kylin.tankwar.model.MissileView;
import com.kylin.tankwar.model.TankView;

public interface IReplication {

	public void replicateTank(TankView view);
	
	public void replicateBlood(BloodView view);
	
	public void replicateExplode(ExplodeView view);
	
	public void replicateMissile(MissileView view);
}
