package com.kylin.jbosscache.api;

import org.jboss.cache.Fqn;

public class FqnAPITest {

	public static void main(String[] args) {
		
		FqnAPITest test = new FqnAPITest();
		
		test.createFqn();
	}

	private void createFqn() {
		Fqn strFqn = Fqn.fromString("/people/Smith/Joe/");
		Fqn eleDqn = Fqn.fromElements("accounts", "NY", new Integer(12345));
	}

}
