package com.kylin.jgroups.test.underlying;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Msg implements Serializable {

	private static final long serialVersionUID = 7173095672760761226L;
	
	Set<String> set = new HashSet<String>();
	
	public Msg(int size) {
		for(int i= 0 ; i < size ; i ++) {
			set.add(UUID.randomUUID().toString());
		}
	}
	
	public long size() {
		long size = 0;
		
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			size = baos.size();
		} catch (IOException e) {
			throw new RuntimeException("count msg size", e);
		} finally {
			
			try {
				if(null != oos){
					oos.close();
					oos = null;
				}
				if(null != baos){
					baos.close();
					baos = null;
				}
			} catch (IOException e) {
				throw new RuntimeException("close stream", e);
			}
		}
		
		return size;
	}
	
	public String toString() {
		return "Block message, size "  + size();
	}

	public static void main(String[] args) throws IOException {
						
		System.out.println(new Msg(1));
		System.out.println(new Msg(10));
		System.out.println(new Msg(100));
		System.out.println(new Msg(1000));
		System.out.println(new Msg(10000));
		System.out.println(new Msg(100000));
		System.out.println(new Msg(1000000));
	}

}
