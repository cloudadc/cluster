package com.kylin.tankwar.core;

import java.util.List;
import java.util.Map;

import com.kylin.tankwar.model.Blood;
import com.kylin.tankwar.model.Explode;
import com.kylin.tankwar.model.Missile;
import com.kylin.tankwar.model.Tank;

public interface ITank {

	public Map<String, Tank> getTankMap();
	
	public void put(String key, Tank value);
	
	public Map<String, Missile> getMissileMap();
	
	public void put(String key, Missile value);
	
	public List<Explode> getExplodes();
	
	public void add(Explode explode);
	
	public List<Blood> getBloods();
	
	public void add(Blood blood);
	
	public String getName();
	
	public int getMemberSize();
}
