package com.kylin.infinispan.common;

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

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getCareer() {
		return career;
	}

	public User setCareer(String career) {
		this.career = career;
		return this;
	}

	public String toString() {
		return "User [id=" + id + ", name=" + name + ", career=" + career + "]";
	}

}
