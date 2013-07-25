package org.infinispan.grid.demo.test;

import java.util.HashSet;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		System.out.println(1 << 5);
		System.out.println(1 << 8);
		
		Set<String> keys = new HashSet<String>();
		keys.add("ad");
		keys.add("123");
		
		String prompt = keys.size() > 1 ? "entities" : "entity";
		System.out.println(prompt);
	}

}
