package com.kylin.jgroups;

public abstract class TestBase {
	
	User user = new User(100, "Kylin Soong", "IT");

	public abstract void test() throws Exception;
}
