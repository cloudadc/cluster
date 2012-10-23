package com.kylin.infinispan.helloworld;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 4956940446956240339L;
	
	private int id;
	
	private String name;
	
	private String career;

	public User(int id, String name, String career) {
		super();
		this.id = id;
		this.name = name;
		this.career = career;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String toString() {
		return "User [id=" + id + ", name=" + name + ", career=" + career + "]";
	}

}
