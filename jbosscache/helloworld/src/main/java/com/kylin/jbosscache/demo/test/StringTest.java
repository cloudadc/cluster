package com.kylin.jbosscache.demo.test;

public class StringTest {

	public static void main(String[] args) {

		String path = "/";
		
		String[] array = path.split("/");
		
		for(int i = 0 ; i < array.length ; i ++) {
			System.out.println(array[i]);
		}
		
		System.out.println(array.length);
	}

}
