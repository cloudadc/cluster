package com.kylin.tankwar.test;

import com.kylin.tankwar.core.Point;

public class ArrayTest {

	public static void  main(String[] args) {
		
		Point[] tmp = new Point[75];
		Point[] array = new Point[150];
		
		for(int i = 0 ; i < 75 ; i ++){
			Point p = new Point(350 + i, 300 - i);
			tmp[i] = p;
			array[i] = p;
		}
		
		for(int i = 75 ; i > 0 ; i --) {
			array[150 - i] = tmp[i - 1];
		}
		
		for(int i = 0 ; i < array.length ; i ++) {
			System.out.println(i + " -> " + array[i]);
		}
	}
}
