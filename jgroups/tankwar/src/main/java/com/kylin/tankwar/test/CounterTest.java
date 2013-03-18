package com.kylin.tankwar.test;

import com.kylin.tankwar.model.Counter;

public class CounterTest {

	public static void main(String[] args) {

		System.out.println(Counter.TANK_ID_GEN.getAndIncrement());
		System.out.println(Counter.TANK_ID_GEN.getAndIncrement());
		
		System.out.println(Counter.MISSILE_ID_GEN.getAndIncrement());
		System.out.println(Counter.MISSILE_ID_GEN.getAndIncrement());
		
		System.out.println(Counter.EXPLODE_ID_GEN.getAndIncrement());
		System.out.println(Counter.EXPLODE_ID_GEN.getAndIncrement());
	}

}
