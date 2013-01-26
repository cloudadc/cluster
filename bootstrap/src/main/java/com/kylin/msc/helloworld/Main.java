package com.kylin.msc.helloworld;

public class Main {
	
	private final static String VERSION = "1.0.0";

	public static void main(String[] args) {
		
		System.out.printf("\n    Modular Service Container HelloWorld Demo %s\n\n", VERSION);
	
		new PrintClassLoader().print();
	}

}
