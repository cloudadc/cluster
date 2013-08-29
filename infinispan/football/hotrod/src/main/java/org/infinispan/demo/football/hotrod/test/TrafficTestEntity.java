package org.infinispan.demo.football.hotrod.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrafficTestEntity implements Serializable {
	
	private static final long serialVersionUID = 941481461661910564L;
	
	private List<String> content = new ArrayList<String>(1000);

	public TrafficTestEntity() {
		for(int i = 0 ; i < 1000 ; i ++) {
			content.add(UUID.randomUUID().toString());
		}
	}
	
	public String key() {
		return UUID.randomUUID().toString();
	}
	
	public Object value() {
		return this;
	}
	
	public static void main(String[] args) {
		TrafficTestEntity entity = new TrafficTestEntity();
		System.out.println(entity.key());
		System.out.println(entity.value());
	}

}
