package com.kylin.tankwar.core;

import java.util.Map;

import com.kylin.tankwar.model.Tank;

public interface ITank {

	public Map<String, Tank> getTankMap();
	
	public void put(String key, Tank value);
}
