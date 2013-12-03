package org.jboss.demo.cluster.jpa.test;

import java.util.Random;

public class Test {

	public static void main(String[] args) {

		Random r = new Random();
		for(int i = 0 ; i < 100 ; i ++) {
			System.out.println(i + ": " + r.nextInt(5));
		}
	}

}
