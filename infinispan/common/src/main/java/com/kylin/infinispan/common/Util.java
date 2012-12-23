package com.kylin.infinispan.common;

public class Util {

	public static void print(Object obj) {
		System.out.print(obj);
	}
	
	public static void println(Object obj) {
		System.out.println(obj);
	}
	
	@SuppressWarnings("static-access")
	public static void sleep(long time) {
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
			throw new RuntimeException("Thread [" + Thread.currentThread().getName() + "] was Interrupted", e);
		}
	}
}
