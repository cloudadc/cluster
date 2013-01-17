package com.kylin.jbosscache.test;

public class HookTest {

	public static void main(String[] args) {
		
		for(int i = 0 ; i < 5 ; i ++) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " Pause");
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				for(int i = 0 ; i < 5 ; i ++) {
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + " Pause");
				}
			}
		});
		
		System.out.println(Thread.currentThread().getName() + " Exit");
	}

}
