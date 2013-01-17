package com.kylin.jbosscache.demo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.xml.DOMConfigurator;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;

import com.kylin.jbosscache.demo.Content;

public class CacheTest {
	
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	
	Cache<String, String> cache ;
	
	Fqn<String> fqn1 = Fqn.fromString("/root");
	Fqn<String> fqn2 = Fqn.fromString("/root/branch");
	Fqn<String> fqn3 = Fqn.fromString("/root/branch/leaf");
	Fqn<String> fqn4 = Fqn.fromString("/root/trunk");
	Fqn<String> fqn5 = Fqn.fromString("/root/trunk/branch");
	Fqn<String> fqn6 = Fqn.fromString("/root/trunk/leaf");
	Fqn<String> fqn7 = Fqn.fromString("/root/trunk/branch/leaf");
	Fqn<String> fqn8 = Fqn.fromString("/root/leaf");
	
	Fqn<String> fqn11 = Fqn.fromString("/root1");
	Fqn<String> fqn12 = Fqn.fromString("/root1/branch");
	Fqn<String> fqn13 = Fqn.fromString("/root1/branch/leaf");
	
	protected void init() {
		CacheFactory<String, String> factory = new DefaultCacheFactory<String, String>();
		cache = factory.createCache("total-replication.xml", false);
		cache.start();
	}
	
	public void addContent() {
		
		cache.put(fqn1, "key", new Content(fqn1.toString()).toString());
		cache.put(fqn2, "key", new Content(fqn2.toString()).toString());
		cache.put(fqn3, "key", new Content(fqn3.toString()).toString());
		cache.put(fqn4, "key", new Content(fqn4.toString()).toString());
		cache.put(fqn5, "key", new Content(fqn5.toString()).toString());
		cache.put(fqn6, "key", new Content(fqn6.toString()).toString());
		cache.put(fqn7, "key", new Content(fqn7.toString()).toString());
		cache.put(fqn8, "key", new Content(fqn8.toString()).toString());
		
		System.out.println(cache.get(fqn1, "key"));
		System.out.println(cache.get(fqn2, "key"));
		System.out.println(cache.get(fqn3, "key"));
		System.out.println(cache.get(fqn4, "key"));
		System.out.println(cache.get(fqn5, "key"));
		System.out.println(cache.get(fqn6, "key"));
		System.out.println(cache.get(fqn7, "key"));
		System.out.println(cache.get(fqn8, "key"));
		
		cache.put(fqn11, "key", new Content(fqn11.toString()).toString());
		cache.put(fqn12, "key", new Content(fqn12.toString()).toString());
		cache.put(fqn13, "key", new Content(fqn13.toString()).toString());
		
		System.out.println(cache.get(fqn11, "key"));
		System.out.println(cache.get(fqn12, "key"));
		System.out.println(cache.get(fqn13, "key"));
	}
	
	private void addToList(Fqn<String>  fqn) {
		
		Set<Object> children;

		if (fqn == null) {
			return;
		}

		// 1 . Add myself
		fqnLists.add(fqn);

		// 2. Then add my children
		children = cache.getRoot().getChild(fqn).getChildrenNames();
		if (children != null) {
			for (Object child_name : children) {
				addToList(Fqn.fromRelativeElements(fqn, (String) child_name));
			}
		}
	}
	
	List<Fqn<String>> fqnLists = new ArrayList<Fqn<String>>();
	
	protected void getALlFqn() {
		addToList(cache.getRoot().getFqn());
		for(Object obj : fqnLists) {
			System.out.println(obj);
		}
	}

	public static void main(String[] args) {
	
		CacheTest test = new CacheTest();
		test.init();
		test.addContent();
		test.getALlFqn();
	}
	


}











