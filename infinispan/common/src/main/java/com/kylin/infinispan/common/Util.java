package com.kylin.infinispan.common;

public class Util {
	
	private static final long DEFAULT_SLEEP_TIME = 1000;

	public static void print(Object obj) {
		System.out.print(obj);
	}
	
	public static void println(Object obj) {
		System.out.println(obj);
	}
	
	public static void pause(Object obj) {
		System.out.print(obj);
		sleep(DEFAULT_SLEEP_TIME);
	}
	
	public static void pauseln(Object obj) {
		System.out.println(obj);
		sleep(DEFAULT_SLEEP_TIME);
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
