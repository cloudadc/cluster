package org.jgroups.demo.tankwar.test;

import org.jgroups.demo.tankwar.model.Point;

public class ArrayTest {

	public static void  main(String[] args) {
		
		Point[] tmp = new Point[75];
		Point[] tmpArray = new Point[150];
		Point[] array = new Point[300];
		
		for(int i = 0 ; i < 75 ; i ++){
			Point p = new Point(350 + i, 300 - i);
			tmp[i] = p;
			tmpArray[i] = p;
		}
		
		for(int i = 75 ; i > 0 ; i --) {
			tmpArray[150 - i] = tmp[i - 1];
		}
		
		for(int i = 0, index = 0 ; i < tmpArray.length ; i ++) {
			array[index++] = tmpArray[i];
			array[index++] = tmpArray[i];
		}
		
		for(int i = 0 ; i < array.length ; i ++) {
			System.out.println(i + " -> " + array[i]);
		}
	}
}
