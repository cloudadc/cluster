package org.jgroups.demo.tankwar.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

	public static AtomicInteger TANK_ID_GEN = new AtomicInteger(1);
	
	public static AtomicInteger MISSILE_ID_GEN = new AtomicInteger(1);
	
	public static AtomicInteger EXPLODE_ID_GEN = new AtomicInteger(1);
}
