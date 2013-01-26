package com.kylin.msc.helloworld;

public class PrintClassLoader {

	public static void main(String[] args) {

		new PrintClassLoader().print();
	}

	public void print() {
		
		println("Print ClassLoader: ");
		
		printClassLoader(PrintClassLoader.class.getClassLoader());
		
		println("");
		
		printClassLoader(Thread.currentThread().getContextClassLoader());
	}
	
	public void printClassLoader(ClassLoader loader) {
		println("  " + loader);
		if(loader.getParent() != null) {
			printClassLoader(loader.getParent());
		}
	}
	
	private void println(Object obj) {
		System.out.println(obj);
	}

}
