package org.jgroups.demo.tankwar.test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jgroups.demo.tankwar.model.Wall;


public class ConcurrentModificationTest {

	public void setTest(String prompt) {

		System.out.println("\n---- " + prompt + " -----\n");

		Set<String> test = new HashSet<String>();

		for (int i = 0; i < 6; i++) {
			test.add("str-" + i);
		}

		System.out.println(test);

		try {
			for (String str : test) {
				test.remove("str-5");
				test.remove("str-4");
				System.out.println(str);
			}
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}

		System.out.println(test);

	}

	public void listTest(String prompt) {

		System.out.println("\n---- " + prompt + " -----\n");

		List<String> test = new ArrayList<String>();

		for (int i = 0; i < 6; i++) {
			test.add("str-" + i);
		}

		System.out.println(test);

		try {
			for (int i = 0; i < 6; i++) {
				test.remove("str-5");
				test.remove("str-4");
				System.out.println(test.get(i));
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		System.out.println(test);
	}

	public void mapTest(String prompt) {

		System.out.println("\n---- " + prompt + " -----\n");

		Map<String, Wall> test = new HashMap<String, Wall>();

		for (int i = 0; i < 6; i++) {
			test.put("key-" + i, new Wall(i, i, i, i));
		}

		System.out.println(test);

		try {
			for (int i = 0; i < 6; i++) {
				test.remove("key-5");
				test.remove("key-4");
				System.out.println(test.get("key-" + i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(test);
	}

	public void currentMapTest(String prompt) {

		System.out.println("\n---- " + prompt + " -----\n");

		Map<String, Wall> test = new ConcurrentHashMap<String, Wall>();

		for (int i = 0; i < 6; i++) {
			test.put("key-" + i, new Wall(i, i, i, i));
		}

		System.out.println(test);

		try {
			for (int i = 0; i < 6; i++) {
				test.remove("key-5");
				test.remove("key-4");
				System.out.println(test.get("key-" + i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(test);
	}

	public static void main(String[] args) {

		ConcurrentModificationTest test = new ConcurrentModificationTest();

		test.setTest("HashSet concurrent modification test");

		test.listTest("ArrayList concurrent modification test");

		test.mapTest("HashMap concurrent modification test");
		
		test.currentMapTest("ConcurrentHashMap concurrent modification test");
	}

}
