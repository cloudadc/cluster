package com.kylin.jbosscache.api;

import org.jboss.cache.Fqn;

public class FqnAPITest {

	public static void main(String[] args) {
		
		FqnAPITest test = new FqnAPITest();
		
		test.createFqn();
		
		Fqn root = Fqn.ROOT;
		System.out.println(root.toString());
		System.out.println(root.SEPARATOR);
	}

	private void createFqn() {
		Fqn strFqn = Fqn.fromString("/people/Smith/Joe/");
		System.out.println(strFqn.toString());
		Fqn eleDqn = Fqn.fromElements("accounts", "NY", new Integer(12345));
		System.out.println(eleDqn.toString());
	}

}
