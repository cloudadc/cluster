package com.kylin.jbosscache.demo.test;

import com.customized.tools.cli.test.A;

public class B extends A {

	protected void sayHello() {
		System.out.println(B.class);
	}

	public void testPublic() {
		System.out.println(B.class);
	}

	public static void main(String[] args) {
		
		B test = new B();
		test.sayHello();
		System.out.println(test.echo("test"));
		test.testPublic();
	}

}
